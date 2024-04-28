package io.azar.examples.webfluxholyquran;

import io.azar.examples.webfluxholyquran.dto.ApiError;
import io.azar.examples.webfluxholyquran.dto.CreateSurahDTO;
import io.azar.examples.webfluxholyquran.service.QuranService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {"api.quranapi.host=http://localhost:${wiremock.server.port}"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"unit-test"})
public class WebfluxQuranApiApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private QuranService quranService;

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
                .expectBody()
                .jsonPath("$.code").isEqualTo(200)
                .jsonPath("$.status").isEqualTo("OK")
                .jsonPath("$.data.number").isEqualTo(114)
                .jsonPath("$.data.name").isEqualTo("سُورَةُ النَّاسِ")
                .jsonPath("$.data.ayahs.length()").isEqualTo(6)
                .jsonPath("$.data.ayahs[0].number").isEqualTo(6231)
                .jsonPath("$.data.ayahs[0].text").isEqualTo("SAY: \"I seek refuge with the Sustainer of men,")
                .jsonPath("$.data.ayahs[0].juz").isEqualTo(30)
                .jsonPath("$.data.ayahs[0].manzil").isEqualTo(7)
                .jsonPath("$.data.ayahs[0].page").isEqualTo(604)
                .jsonPath("$.data.ayahs[0].ruku").isEqualTo(556)
                .jsonPath("$.data.ayahs[0].sajda").isEqualTo(false)
                .jsonPath("$.data.ayahs[0].numberInSurah").isEqualTo(1)
                .jsonPath("$.data.ayahs[0].hizbQuarter").isEqualTo(240)
                .jsonPath("$.data.ayahs[1].number").isEqualTo(6232)
                .jsonPath("$.data.ayahs[1].text").isEqualTo("\"the Sovereign of men,")
                .jsonPath("$.data.ayahs[1].juz").isEqualTo(30)
                .jsonPath("$.data.ayahs[1].manzil").isEqualTo(7)
                .jsonPath("$.data.ayahs[1].page").isEqualTo(604)
                .jsonPath("$.data.ayahs[1].ruku").isEqualTo(556)
                .jsonPath("$.data.ayahs[1].sajda").isEqualTo(false)
                .jsonPath("$.data.ayahs[1].numberInSurah").isEqualTo(2)
                .jsonPath("$.data.ayahs[1].hizbQuarter").isEqualTo(240)
                .jsonPath("$.data.ayahs[2].number").isEqualTo(6233)
                .jsonPath("$.data.ayahs[2].text").isEqualTo("\"the God of men,")
                .jsonPath("$.data.ayahs[2].juz").isEqualTo(30)
                .jsonPath("$.data.ayahs[2].manzil").isEqualTo(7)
                .jsonPath("$.data.ayahs[2].page").isEqualTo(604)
                .jsonPath("$.data.ayahs[2].ruku").isEqualTo(556)
                .jsonPath("$.data.ayahs[2].sajda").isEqualTo(false)
                .jsonPath("$.data.ayahs[2].numberInSurah").isEqualTo(3)
                .jsonPath("$.data.ayahs[2].hizbQuarter").isEqualTo(240)
                .jsonPath("$.data.ayahs[3].number").isEqualTo(6234)
                .jsonPath("$.data.ayahs[3].text").isEqualTo("\"from the evil of the whispering, elusive tempter")
                .jsonPath("$.data.ayahs[3].juz").isEqualTo(30)
                .jsonPath("$.data.ayahs[3].manzil").isEqualTo(7)
                .jsonPath("$.data.ayahs[3].page").isEqualTo(604)
                .jsonPath("$.data.ayahs[3].ruku").isEqualTo(556)
                .jsonPath("$.data.ayahs[3].sajda").isEqualTo(false)
                .jsonPath("$.data.ayahs[3].numberInSurah").isEqualTo(4)
                .jsonPath("$.data.ayahs[3].hizbQuarter").isEqualTo(240)
                .jsonPath("$.data.ayahs[4].number").isEqualTo(6235)
                .jsonPath("$.data.ayahs[4].text").isEqualTo("\"who whispers in the hearts of men")
                .jsonPath("$.data.ayahs[4].juz").isEqualTo(30)
                .jsonPath("$.data.ayahs[4].manzil").isEqualTo(7)
                .jsonPath("$.data.ayahs[4].page").isEqualTo(604)
                .jsonPath("$.data.ayahs[4].ruku").isEqualTo(556)
                .jsonPath("$.data.ayahs[4].sajda").isEqualTo(false)
                .jsonPath("$.data.ayahs[4].numberInSurah").isEqualTo(5)
                .jsonPath("$.data.ayahs[4].hizbQuarter").isEqualTo(240)
                .jsonPath("$.data.ayahs[5].number").isEqualTo(6236)
                .jsonPath("$.data.ayahs[5].text").isEqualTo("\"from all [temptation to evil by] invisible forces as well as men,\"")
                .jsonPath("$.data.ayahs[5].juz").isEqualTo(30)
                .jsonPath("$.data.ayahs[5].manzil").isEqualTo(7)
                .jsonPath("$.data.ayahs[5].page").isEqualTo(604)
                .jsonPath("$.data.ayahs[5].ruku").isEqualTo(556)
                .jsonPath("$.data.ayahs[5].sajda").isEqualTo(false)
                .jsonPath("$.data.ayahs[5].numberInSurah").isEqualTo(6)
                .jsonPath("$.data.ayahs[5].hizbQuarter").isEqualTo(240)
                .jsonPath("$.data.edition.identifier").isEqualTo("en.asad")
                .jsonPath("$.data.edition.language").isEqualTo("en")
                .jsonPath("$.data.edition.name").isEqualTo("Asad")
                .jsonPath("$.data.edition.format").isEqualTo("text")
                .jsonPath("$.data.edition.type").isEqualTo("translation")
                .jsonPath("$.data.edition.direction").isEqualTo("ltr")
                .jsonPath("$.data.edition.englishName").isEqualTo("Muhammad Asad")
                .jsonPath("$.data.englishName").isEqualTo("An-Naas")
                .jsonPath("$.data.englishNameTranslation").isEqualTo("Mankind")
                .jsonPath("$.data.revelationType").isEqualTo("Meccan")
                .jsonPath("$.data.numberOfAyahs").isEqualTo(6);


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
                .expectBody()
                .jsonPath("$.code").isEqualTo("23")
                .jsonPath("$.message").isEqualTo("Chapter doesn't exist in target system.");
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


    @Test
    public void saveSurah_ValidInput_ReturnsCreated() {
        // When a POST request is sent to the /v1/surah endpoint
        webTestClient.post()
                .uri("/v1/surah")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("X-API-Key", UUID.randomUUID().toString())
                .bodyValue(validSaveSurahRequest())
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void saveSurah_InvalidInput_ReturnsBadRequest() {
        // When a POST request is sent to the /v1/surah endpoint
        webTestClient.post()
                .uri("/v1/surah")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("X-API-Key", UUID.randomUUID().toString())
                .bodyValue(invalidSaveSurahRequest())
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void missingHeaderTest() {
        // When a POST request is sent to the /v1/surah endpoint
        webTestClient.post()
                .uri("/v1/surah")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(new CreateSurahDTO())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.code" ).isEqualTo("21")
                .jsonPath("$.message").isEqualTo( "Missing header: X-API-Key");
    }

    @Test
    public void invalidHeaderValueTest() {

        // When a POST request is sent to the /v1/surah endpoint
        webTestClient.post()
                .uri("/v1/surah")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("X-API-Key", "INVald##DATA()))))))))))))")
                .bodyValue(validSaveSurahRequest())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.code" ).isEqualTo("26")
                .jsonPath("$.message").isEqualTo( "Invalid header: X-API-Key");
    }


    CreateSurahDTO validSaveSurahRequest(){
        CreateSurahDTO createSurahDTO = new CreateSurahDTO();

        createSurahDTO.setNumber(1);
        createSurahDTO.setName("الإخلاص");
        createSurahDTO.setEnglishName("Al-Ikhlas");
        createSurahDTO.setEnglishNameTranslation("Sincerity");
        createSurahDTO.setRevelationType("Meccan");
        createSurahDTO.setNumberOfAyahs(4);

        List<CreateSurahDTO.Ayah> ayahs = new ArrayList<>();

        CreateSurahDTO.Ayah ayah1 = new CreateSurahDTO.Ayah();
        ayah1.setNumber(1);
        ayah1.setText("قُلْ هُوَ اللَّهُ أَحَدٌ");
        ayah1.setNumberInSurah(1);
        ayah1.setJuz(30);
        ayah1.setManzil(60);
        ayah1.setPage(604);
        ayah1.setRuku(1);
        ayah1.setHizbQuarter(1);
        ayah1.setSajda(false);

        ayahs.add(ayah1);


        CreateSurahDTO.Edition edition = new CreateSurahDTO.Edition();
        edition.setIdentifier("en.asad");
        edition.setLanguage("English");
        edition.setName("The Message of the Quran");
        edition.setEnglishName("The Message of the Quran");
        edition.setFormat("text");
        edition.setType("translation");
        edition.setDirection("ltr");

        createSurahDTO.setEdition(edition);

        return createSurahDTO;
    }

    private CreateSurahDTO invalidSaveSurahRequest() {
        // Given an invalid CreateSurahDTO object
        CreateSurahDTO createSurahDTO = new CreateSurahDTO();

        createSurahDTO.setNumber(1);
        createSurahDTO.setName("الإخلاص");
        createSurahDTO.setEnglishName("Al-IkhlasINVald##DATA()))))))))))))");
        createSurahDTO.setEnglishNameTranslation("Sincerity");
        createSurahDTO.setRevelationType("Meccan");
        createSurahDTO.setNumberOfAyahs(4);

        List<CreateSurahDTO.Ayah> ayahs = new ArrayList<>();

        CreateSurahDTO.Ayah ayah1 = new CreateSurahDTO.Ayah();
        ayah1.setNumber(1);
        ayah1.setText("قُلْ هُوَ اللَّهُ أَحَدٌ");
        ayah1.setNumberInSurah(1);
        ayah1.setJuz(30);
        ayah1.setManzil(60);
        ayah1.setPage(604);
        ayah1.setRuku(1);
        ayah1.setHizbQuarter(1);
        ayah1.setSajda(false);

        ayahs.add(ayah1);

        CreateSurahDTO.Edition edition = new CreateSurahDTO.Edition();
        edition.setIdentifier("en.asad");
        edition.setLanguage("English");
        edition.setName("The Message of the Quran");
        edition.setEnglishName("The Message of the Quran");
        edition.setFormat("text");
        edition.setType("translation");
        edition.setDirection("ltr");

        createSurahDTO.setEdition(edition);

        return createSurahDTO;
    }
}
