package io.azar.examples.webfluxholyquran.repository;

import io.azar.examples.webfluxholyquran.config.ApiConfigurationProperties;
import io.azar.examples.webfluxholyquran.dto.ApiError;
import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Repository
public class QuranRepositoryImpl implements QuranRepository {
    @Autowired
    private WebClient webClient;
    @Autowired
    private ApiConfigurationProperties apiConfigurationProperties;

    @Override
    public Mono<SurahResponseDto> findSurahByChapterAndEdition(Integer surah, String edition) {
        String apiEndpoint = String.format(apiConfigurationProperties.getQuranapi().getHost() + "/v1/surah/%d/%s", surah, edition);
        return webClient.get()
                .uri(apiEndpoint)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        this::handleClientError
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        this::handleServerError
                )
                .bodyToMono(SurahResponseDto.class);
    }
    private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ApiError.class)
                .flatMap(apiError -> Mono.error(
                        new ResponseStatusException(
                                HttpStatusCode.valueOf(clientResponse.statusCode().value()),
                                apiError.getMessage()
                        )
                ));
    }

    private Mono<? extends Throwable> handleServerError(ClientResponse clientResponse) {
        // Handle 5xx errors, if needed
        return clientResponse.bodyToMono(ApiError.class)
                .flatMap(apiError -> Mono.error(
                        new ResponseStatusException(
                                HttpStatusCode.valueOf(clientResponse.statusCode().value()),
                                apiError.getMessage()
                        )
                ));
    }
}