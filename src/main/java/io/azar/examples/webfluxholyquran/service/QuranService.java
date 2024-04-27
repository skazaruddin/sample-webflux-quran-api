package io.azar.examples.webfluxholyquran.service;

import io.azar.examples.webfluxholyquran.dto.CreateSurahDTO;
import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import reactor.core.publisher.Mono;

public interface QuranService {

	Mono<SurahResponseDto> findSurahByChapterAndEdition(int chapter, String edition);

	Mono<Void> createSurah(CreateSurahDTO createSurahDTO);

}
