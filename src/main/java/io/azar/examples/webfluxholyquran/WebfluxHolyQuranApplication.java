package io.azar.examples.webfluxholyquran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class WebfluxHolyQuranApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebfluxHolyQuranApplication.class, args);
	}
}
