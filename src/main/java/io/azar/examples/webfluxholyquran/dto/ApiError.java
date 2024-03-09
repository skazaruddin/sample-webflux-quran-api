package io.azar.examples.webfluxholyquran.dto;

import lombok.Data;

@Data
public class ApiError {
    private String code;
    private String message;
}
