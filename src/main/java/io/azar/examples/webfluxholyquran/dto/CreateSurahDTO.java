package io.azar.examples.webfluxholyquran.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CreateSurahDTO {

	private static final int MAX_CHAPTER_NO = 114;

	private static final int MIN_CHAPTER_NO = 1;

	@Max(value = MAX_CHAPTER_NO)
	@Min(value = MIN_CHAPTER_NO)
	private int number;

	@Pattern(regexp = "\\w")
	private String name;

	@Pattern(regexp = "\\w")
	@JsonProperty("englishName")
	private String englishName;

	@Pattern(regexp = "\\w")
	@JsonProperty("englishNameTranslation")
	private String englishNameTranslation;

	@Pattern(regexp = "\\w")
	@JsonProperty("revelationType")
	private String revelationType;

	@JsonProperty("numberOfAyahs")
	private int numberOfAyahs;

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
