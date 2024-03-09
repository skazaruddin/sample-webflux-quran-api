package io.azar.examples.webfluxholyquran.controller;

import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import io.azar.examples.webfluxholyquran.service.QuranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/surah")
public class QuranController {
    private QuranService quranService;

    public QuranController(QuranService quranService) {
        this.quranService = quranService;
    }

    /**
     * Retrieve information about a specific chapter (Surah) from the Quran based on the provided chapter number
     * and the edition of the translation.
     *
     * @param chapter The chapter number for which the information is requested.
     * @param edition The edition or translator name used for the translation of the Quranic text.
     * @return A {@link Mono} containing a {@link SurahResponseDto} representing the response.
     *         The Mono may emit the Surah information if found, or an empty result if not found.
     *         The Mono can also represent any errors during the retrieval process.
     *
     * @see SurahResponseDto
     * @see QuranService#findSurahByChapterAndEdition(int, String)
     */
    @GetMapping("/{chapter}/{edition}")
    public Mono<SurahResponseDto> getSurah(
            @PathVariable int chapter,
            @PathVariable String edition) {
        return quranService.findSurahByChapterAndEdition(chapter, edition);
    }

}
