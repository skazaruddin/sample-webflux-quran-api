package io.azar.examples.webfluxholyquran.service;

import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import io.azar.examples.webfluxholyquran.repository.QuranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class QuranServiceImpl implements QuranService {

    @Autowired
    private QuranRepository quranRepository;
    @Override
    public Mono<SurahResponseDto> findSurahByChapterAndEdition(int chapter, String edition) {
        return quranRepository.findSurahByChapterAndEdition(chapter, edition);
    }
}
