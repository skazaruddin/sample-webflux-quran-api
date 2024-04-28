package io.azar.examples.webfluxholyquran.controller;

import io.azar.examples.webfluxholyquran.dto.CreateSurahDTO;
import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import io.azar.examples.webfluxholyquran.service.QuranService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/surah")
@Validated
public class QuranController {

	private QuranService quranService;

	public QuranController(QuranService quranService) {
		this.quranService = quranService;
	}

	/**
	 * Retrieve information about a specific chapter (Surah) from the Quran based on the
	 * provided chapter number and the edition of the translation.
	 * @param chapter The chapter number for which the information is requested.
	 * @param edition The edition or translator name used for the translation of the
	 * Quranic text.
	 * @return A {@link Mono} containing a {@link SurahResponseDto} representing the
	 * response. The Mono may emit the Surah information if found, or an empty result if
	 * not found. The Mono can also represent any errors during the retrieval process.
	 *
	 * @see SurahResponseDto
	 * @see QuranService#findSurahByChapterAndEdition(int, String)
	 */
	@GetMapping("/{chapter}/{edition}")
	public Mono<SurahResponseDto> getSurah(@PathVariable int chapter, @PathVariable String edition) {
		return quranService.findSurahByChapterAndEdition(chapter, edition);
	}

	@PostMapping
	public Mono<ResponseEntity<Void>> saveSurah(@Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$") @RequestHeader(name = "X-API-Key") String xapiKey,
												@Valid @RequestBody CreateSurahDTO createSurahDTO) {

		return quranService.createSurah(createSurahDTO)
				.map(userUUID -> UriComponentsBuilder.fromPath(("/{id}")).buildAndExpand(userUUID).toUri())
				.map(uri -> ResponseEntity.created(uri).build());
	}
}
