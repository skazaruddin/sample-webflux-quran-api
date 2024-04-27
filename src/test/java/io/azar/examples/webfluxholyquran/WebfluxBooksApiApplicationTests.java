package io.azar.examples.webfluxholyquran;

import io.azar.examples.webfluxholyquran.dto.ApiError;
import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = { "api.quranapi.host=http://localhost:${wiremock.server.port}" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "unit-test" })
public class WebfluxBooksApiApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	/**
	 * Tests the successful retrieval of Surah information from the API.
	 * <p>
	 * This test case performs an HTTP GET request to the Surah endpoint of the API and
	 * expects a successful response. It verifies the correctness of the returned
	 * SurahResponseDto object and its associated fields.
	 */
	@Test
	public void shouldRetrieveSurahInformationSuccessfullyTest() {
		int chapter = 114;
		String edition = "en.asad";

		// Perform the HTTP GET request using WebTestClient
		webTestClient.get()
			.uri("/v1/surah/{chapter}/{edition}", chapter, edition)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(SurahResponseDto.class)
			.consumeWith(response -> {
				SurahResponseDto surahResponseDto = response.getResponseBody();
				Assertions.assertNotNull(surahResponseDto);
				Assertions.assertEquals(200, surahResponseDto.getCode());
				Assertions.assertEquals("OK", surahResponseDto.getStatus());

				// Assertions for data field
				Assertions.assertNotNull(surahResponseDto.getData());
				Assertions.assertEquals(114, surahResponseDto.getData().getNumber());
				Assertions.assertEquals("سُورَةُ النَّاسِ", surahResponseDto.getData().getName());

				// Assertions for ayahs field
				Assertions.assertNotNull(surahResponseDto.getData().getAyahs());
				Assertions.assertEquals(6, surahResponseDto.getData().getAyahs().size());

				// Assertions for the first ayah -- For demo project perspective just
				// asserted first Ayah, all ayah in reality should have been asserted.
				SurahResponseDto.Ayah firstAyah = surahResponseDto.getData().getAyahs().get(0);
				Assertions.assertEquals(6231, firstAyah.getNumber());
				Assertions.assertEquals("SAY: \"I seek refuge with the Sustainer of men,", firstAyah.getText());
				Assertions.assertEquals(30, firstAyah.getJuz());
				Assertions.assertEquals(7, firstAyah.getManzil());
				Assertions.assertEquals(604, firstAyah.getPage());
				Assertions.assertEquals(556, firstAyah.getRuku());
				Assertions.assertFalse(firstAyah.isSajda());
				Assertions.assertEquals(1, firstAyah.getNumberInSurah());
				Assertions.assertEquals(240, firstAyah.getHizbQuarter());

				// Assertions for edition field
				Assertions.assertNotNull(surahResponseDto.getData().getEdition());
				Assertions.assertEquals("en.asad", surahResponseDto.getData().getEdition().getIdentifier());
				Assertions.assertEquals("en", surahResponseDto.getData().getEdition().getLanguage());
				Assertions.assertEquals("Asad", surahResponseDto.getData().getEdition().getName());
				Assertions.assertEquals("text", surahResponseDto.getData().getEdition().getFormat());
				Assertions.assertEquals("translation", surahResponseDto.getData().getEdition().getType());
				Assertions.assertEquals("ltr", surahResponseDto.getData().getEdition().getDirection());
				Assertions.assertEquals("Muhammad Asad", surahResponseDto.getData().getEdition().getEnglishName());

				// Assertions for English Name
				Assertions.assertEquals("An-Naas", surahResponseDto.getData().getEnglishName());
				Assertions.assertEquals("Mankind", surahResponseDto.getData().getEnglishNameTranslation());
				Assertions.assertEquals("Meccan", surahResponseDto.getData().getRevelationType());
				Assertions.assertEquals(6, surahResponseDto.getData().getNumberOfAyahs());

			});
	}

	/**
	 * Tests the behavior of the Surah endpoint when providing a bad request.
	 * <p>
	 * This test case performs an HTTP GET request to the Surah endpoint with an invalid
	 * chapter number. It expects the endpoint to return a 400 Bad Request status along
	 * with an error message indicating that the requested chapter does not exist in the
	 * target system.
	 */
	@Test
	public void shouldReturnBadRequestForInvalidChapterNumberTest() {
		int chapter = 1141;
		String edition = "en.asad";

		// Perform the HTTP GET request using WebTestClient
		webTestClient.get()
			.uri("/v1/surah/{chapter}/{edition}", chapter, edition)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isBadRequest()
			.expectBody(ApiError.class)
			.consumeWith(response -> {
				ApiError apiError = response.getResponseBody();
				Assertions.assertNotNull(apiError);
				Assertions.assertEquals("23", apiError.getCode());
				Assertions.assertEquals("Chapter doesn't exist in target system.", apiError.getMessage());
			});
	}

	/**
	 * Tests the behavior of the Surah endpoint when the service is temporarily
	 * unavailable.
	 * <p>
	 * This test case performs an HTTP GET request to the Surah endpoint with a chapter
	 * number while service doesn't have capacity to handle more requests. It expects the
	 * endpoint to return a 5xx Server Error status along with an error message indicating
	 * that the service is temporarily unavailable.
	 */
	@Test
	public void shouldReturnServiceUnavailableTest() {
		int chapter = 1150;
		String edition = "en.asad";

		// Perform the HTTP GET request using WebTestClient
		webTestClient.get()
			.uri("/v1/surah/{chapter}/{edition}", chapter, edition)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.is5xxServerError()
			.expectBody(ApiError.class)
			.consumeWith(response -> {
				ApiError apiError = response.getResponseBody();
				Assertions.assertNotNull(apiError);
				Assertions.assertEquals("05", apiError.getCode());
				Assertions.assertEquals("The service is temporarily unavailable.", apiError.getMessage());
			});
	}

}
