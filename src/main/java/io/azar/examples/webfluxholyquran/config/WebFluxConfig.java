package io.azar.examples.webfluxholyquran.config;

import io.azar.examples.webfluxholyquran.util.CertUtils;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Autowired
    private ApiConfigurationProperties apiConfigurationProperties;


    /**
     * Configures and provides a WebClient instance for making HTTP requests.
     * <p>
     * <b>Steps:</b>
     * <ol>
     *   <li><b>Load Truststore Certificates:</b> Use the loadCertificates method to obtain a list of X.509 certificates from the concatenated PEM certificates.</li>
     *   <li><b>Create WebClient HttpClient:</b> Build an instance of HttpClient for WebClient with specific configurations:
     *     <ul>
     *       <li>a. Disable automatic retry on failure.</li>
     *       <li>b. Disable following redirects.</li>
     *       <li>c. Enable keep-alive for persistent connections.</li>
     *       <li>d. Set a response timeout of 50 seconds.</li>
     *       <li>e. Set a connection timeout of 10 seconds.</li>
     *       <li>f. Add read and write timeouts of 50 seconds.</li>
     *       <li>g. Enable TCP keep-alive and disable Nagle's algorithm for lower latency.</li>
     *       <li>h. Secure the client with a custom trust manager for the loaded certificates, using TLSv1.3 and specified ciphers.</li>
     *     </ul>
     *   </li>
     *   <li><b>Build and Return WebClient:</b> Build a WebClient instance with the configured HttpClient, base URL, and default headers.</li>
     * </ol>
     *
     * @return Configured WebClient instance.
     * @throws Exception If there is an issue during truststore creation or HttpClient configuration.
     */
    @Bean
    public WebClient getWebClient() throws Exception {

        List<X509Certificate> x509Certificates = CertUtils.loadCertificates(apiConfigurationProperties.getQuranapi().getTruststore().getCertificates());

        HttpClient client = HttpClient.create()
                .followRedirect(false)
                .disableRetry(true)
                .keepAlive(true)
                .responseTimeout(Duration.ofSeconds(apiConfigurationProperties.getQuranapi().getConnection().getWriteTimeoutSeconds()))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, apiConfigurationProperties.getQuranapi().getConnection().getConnectTimeoutSeconds() * 1000)
                .doOnConnected(connection -> connection
                        .addHandlerFirst(new ReadTimeoutHandler(apiConfigurationProperties.getQuranapi().getConnection().getReadTimeoutSeconds(), TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(apiConfigurationProperties.getQuranapi().getConnection().getWriteTimeoutSeconds(), TimeUnit.SECONDS)))

                .option(ChannelOption.SO_KEEPALIVE, apiConfigurationProperties.getQuranapi().getConnection().isKeepAlive())
                .option(ChannelOption.TCP_NODELAY, apiConfigurationProperties.getQuranapi().getConnection().isTcpNoDelay())

                .secure(sslContextSpec -> sslContextSpec.sslContext(
                        SslContextBuilder.forClient()
                                .trustManager(x509Certificates)
                                .protocols("TLSv1.3")
                                .ciphers(apiConfigurationProperties.getQuranapi().getTruststore().getCiphers())));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl(apiConfigurationProperties.getQuranapi().getHost())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .codecs(
                        clientCodeConfigurer ->{
                            clientCodeConfigurer.customCodecs().register(new Jackson2JsonDecoder());
                            clientCodeConfigurer.customCodecs().register(new Jackson2JsonEncoder());
                        }
                )
                .build();
    }

    @Bean
    public WebExceptionHandler exceptionHandler() {
        return (ServerWebExchange exchange, Throwable ex) -> {
            if (ex instanceof ResponseStatusException) {
                exchange.getResponse().setStatusCode(((ResponseStatusException) ex).getStatusCode());
                return exchange.getResponse().setComplete();
            }
            return Mono.error(ex);
        };
    }
}
