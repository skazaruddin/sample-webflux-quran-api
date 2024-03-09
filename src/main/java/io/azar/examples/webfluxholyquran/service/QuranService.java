package io.azar.examples.webfluxholyquran.service;

import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import reactor.core.publisher.Mono;

public interface QuranService {

    Mono<SurahResponseDto> findSurahByChapterAndEdition(int chapter, String edition);
}
