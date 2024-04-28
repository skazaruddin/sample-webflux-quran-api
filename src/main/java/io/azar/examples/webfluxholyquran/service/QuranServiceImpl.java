package io.azar.examples.webfluxholyquran.service;

import io.azar.examples.webfluxholyquran.dto.CreateSurahDTO;
import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import io.azar.examples.webfluxholyquran.repository.QuranRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
public class QuranServiceImpl implements QuranService {

	@Autowired
	private QuranRepository quranRepository;

	@Override
	public Mono<SurahResponseDto> findSurahByChapterAndEdition(int chapter, String edition) {
		return quranRepository.findSurahByChapterAndEdition(chapter, edition);
	}

	@Override
	public Mono<String> createSurah(CreateSurahDTO createSurahDTO) {
		return Mono.just(UUID.randomUUID().toString());
	}

}
