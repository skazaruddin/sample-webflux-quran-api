package io.azar.examples.webfluxholyquran.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CreateSurahDTO {
	private static final int MIN_CHAPTER_NO = 1;
	private static final int MAX_CHAPTER_NO = 114;

	private static final int MIN_AYAH_NO = 1;
	private static final int MAX_AYAH_NO = 6556;

	@Min(value = MIN_CHAPTER_NO)
	@Max(value = MAX_CHAPTER_NO)
	private int number;

	@Pattern(regexp = "^[\\p{L}-]+$")
	private String name;

	@Pattern(regexp = "^[A-Za-z0-9]+([A-Za-z0-9- ]+)?$")
	private String englishName;

	@Pattern(regexp = "^[A-Za-z0-9]+([A-Za-z0-9- ]+)?$")
	private String englishNameTranslation;

	@Pattern(regexp = "^[A-Za-z]{3,255}$")
	private String revelationType;

	@Min(value = MIN_AYAH_NO)
	@Max(value = MAX_AYAH_NO)
	@JsonProperty("numberOfAyahs")
	private Integer numberOfAyahs;

	private List<CreateSurahDTO.Ayah> ayahs;

	private CreateSurahDTO.Edition edition;

	@lombok.Data
	public static class Ayah {

		private int number;

		private String text;

		@JsonProperty("numberInSurah")
		private int numberInSurah;

		private int juz;

		private int manzil;

		private int page;

		private int ruku;

		@JsonProperty("hizbQuarter")
		private int hizbQuarter;

		private boolean sajda;

		// Constructors, getters, and setters

	}

	@lombok.Data
	public static class Edition {

		private String identifier;

		private String language;

		private String name;

		@JsonProperty("englishName")
		private String englishName;

		private String format;

		private String type;

		private String direction;

		// Constructors, getters, and setters

	}

}
