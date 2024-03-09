package io.azar.examples.webfluxholyquran;

import io.azar.examples.webfluxholyquran.dto.ApiError;
import io.azar.examples.webfluxholyquran.dto.SurahResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "api.alquran.host=http://localhost:${wiremock.server.port}"
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxBooksApiApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testSuccessGetSurahEndpoint() {
        int chapter = 114;
        String edition = "en.asad";

        // Perform the HTTP GET request using WebTestClient
        webTestClient.get()
                .uri("/v1/surah/{chapter}/{edition}", chapter, edition)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SurahResponseDto.class)
                .consumeWith(response -> {
                    SurahResponseDto surahResponseDto = response.getResponseBody();
                    // Add assertions based on the expected response
                    // For example, you can check the values in surahResponseDto
                    // Assertions for the main structure
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

                    // Assertions for the first ayah -- For demo project perspective just asserted first Ayah, all ayah in reality should have been asserted.
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

    @Test
    public void testBadRequestGetSurahEndpoint() {
        int chapter = 1141;
        String edition = "en.asad";

        // Perform the HTTP GET request using WebTestClient
        webTestClient.get()
                .uri("/v1/surah/{chapter}/{edition}", chapter, edition)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ApiError.class)
                .consumeWith(response -> {
                    ApiError apiError = response.getResponseBody();
                    // Add assertions based on the expected response
                    // For example, you can check the values in surahResponseDto
                    // Assertions for the main structure
                    Assertions.assertNotNull(apiError);
                    Assertions.assertEquals("05", apiError.getCode());
                    Assertions.assertEquals("Chapter doesn't exist in target system.", apiError.getMessage());

                });
    }
}
