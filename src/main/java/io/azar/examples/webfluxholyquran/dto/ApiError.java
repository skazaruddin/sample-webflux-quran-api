package io.azar.examples.webfluxholyquran.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

	private String code;

	private String message;

}
