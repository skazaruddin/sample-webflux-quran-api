package io.azar.examples.webfluxholyquran.repository;

import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import reactor.core.publisher.Mono;

public interface QuranRepository {
    Mono<SurahResponseDto> findSurahByChapterAndEdition(Integer surah, String edition);

}
