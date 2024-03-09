package io.azar.examples.webfluxholyquran.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
@Slf4j
class RestExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Void> handleResponseStatusException(ResponseStatusException ex) {
        return notFound().build();
    }

}
