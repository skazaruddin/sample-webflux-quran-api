package io.azar.examples.webfluxholyquran.config;

import io.azar.examples.webfluxholyquran.dto.ApiError;
import io.azar.examples.webfluxholyquran.exceptions.BusinessException;
import io.azar.examples.webfluxholyquran.exceptions.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
class RestExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ApiError>> handleBusinessException(BusinessException ex) {
        return Mono.just(ResponseEntity.badRequest().body(ex.getApiError()));
    }

    @ExceptionHandler(TechnicalException.class)
    public Mono<ResponseEntity<ApiError>> handleTechnicalException(TechnicalException ex) {
        return Mono.just(ResponseEntity.internalServerError().body(ex.getApiError()));
    }

}
