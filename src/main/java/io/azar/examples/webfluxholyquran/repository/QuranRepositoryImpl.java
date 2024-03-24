package io.azar.examples.webfluxholyquran.repository;

import io.azar.examples.webfluxholyquran.config.ApiConfigurationProperties;
import io.azar.examples.webfluxholyquran.dto.ApiError;
import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import io.azar.examples.webfluxholyquran.exceptions.BusinessException;
import io.azar.examples.webfluxholyquran.exceptions.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class QuranRepositoryImpl implements QuranRepository {
    @Autowired
    private WebClient webClient;
    @Autowired
    private ApiConfigurationProperties apiConfigurationProperties;

    @Override
    public Mono<SurahResponseDto> findSurahByChapterAndEdition(Integer surah, String edition) {
        String uri = String.format("/v1/surah/%d/%s", surah, edition);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(ApiError.class).map(BusinessException::new))
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(ApiError.class).map(TechnicalException::new))
                .bodyToMono(SurahResponseDto.class);
    }
}