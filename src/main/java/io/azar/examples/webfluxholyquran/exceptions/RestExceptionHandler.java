package io.azar.examples.webfluxholyquran.exceptions;

import com.fasterxml.jackson.databind.exc.*;
import io.azar.examples.webfluxholyquran.controller.QuranController;
import io.azar.examples.webfluxholyquran.dto.ApiError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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

	@ExceptionHandler(Exception.class)
	public Mono<ResponseEntity<ApiError>> handleAnyException(Exception exception) {
		log.error("Exception", exception);
		return Mono.just(ResponseEntity.internalServerError().body(new ApiError("01", "Internal error")));
	}

	@ExceptionHandler(ServerWebInputException.class)
	public Mono<ResponseEntity<ApiError>> handleServerWebInputException(ServerWebInputException exception) {
		log.error("Exception", exception);
		Throwable rootCause = exception.getRootCause();
		if (rootCause instanceof InvalidFormatException) {
			InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
			return Mono.just(ResponseEntity.badRequest()
				.body(new ApiError("24",
						"Invalid body field: ".concat(invalidFormatException.getPath().get(0).getFieldName()))));
		}
		if (rootCause instanceof UnrecognizedPropertyException) {
			UnrecognizedPropertyException unrecognizedPropertyException = (UnrecognizedPropertyException) rootCause;
			return Mono.just(ResponseEntity.badRequest()
				.body(new ApiError("24",
						"Invalid body field: ".concat(unrecognizedPropertyException.getPropertyName()))));
		}
		else if (rootCause instanceof IgnoredPropertyException) {
			IgnoredPropertyException ignoredPropertyException = (IgnoredPropertyException) rootCause;
			return Mono.just(ResponseEntity.badRequest()
				.body(new ApiError("24", "Ignored body field: ".concat(ignoredPropertyException.getPropertyName()))));
		}
		else if (rootCause instanceof InvalidNullException) {
			InvalidNullException invalidNullException = (InvalidNullException) rootCause;
			return Mono.just(ResponseEntity.badRequest()
				.body(new ApiError("24",
						"Invalid body field: ".concat(invalidNullException.getPath().get(0).getFieldName()))));
		}
		else if (rootCause instanceof InvalidTypeIdException) {
			InvalidTypeIdException invalidTypeIdException = (InvalidTypeIdException) rootCause;
			return Mono.just(ResponseEntity.badRequest()
				.body(new ApiError("24",
						"Invalid body field: ".concat(invalidTypeIdException.getPath().get(0).getFieldName()))));
		}

		String reason = exception.getReason();
		switch (reason) {
			case "No request body":
				return Mono.just(ResponseEntity.badRequest().body(new ApiError("22", "Missing request body")));
			case "Failed to read HTTP message":
				return Mono.just(ResponseEntity.badRequest().body(new ApiError("23", "Invalid request body")));
			default:
				return Mono.just(ResponseEntity.internalServerError().body(new ApiError("01", "Internal error")));
		}
	}

	@ExceptionHandler(org.springframework.web.server.MissingRequestValueException.class)
	public Mono<ResponseEntity<ApiError>> handleMissingRequestValueException(
			org.springframework.web.server.MissingRequestValueException ex) {
		String label = ex.getLabel();
		switch (label) {
			case "header":
				return Mono.just(
						ResponseEntity.badRequest().body(new ApiError("21", "Missing header: ".concat(ex.getName()))));
			default:
				return Mono.just(ResponseEntity.internalServerError().body(new ApiError("01", "Internal error")));
		}
	}

	@ExceptionHandler(WebExchangeBindException.class)
	public Mono<ResponseEntity<ApiError>> handleWebExchangeBindException(WebExchangeBindException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		String errorFields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));

		return Mono.just(ResponseEntity.badRequest()
			.body(new ApiError("10", "Invalid body fields: ".concat(errorFields))
			));
	}


	@ExceptionHandler(ConstraintViolationException.class)
	public Mono<ResponseEntity<ApiError>> handleConstraintViolation(ConstraintViolationException e) {
		ConstraintViolation constraintViolation = (ConstraintViolation )e.getConstraintViolations().toArray()[0];

		if(constraintViolation.getRootBean() instanceof QuranController) {
			PathImpl path = (PathImpl) constraintViolation.getPropertyPath();
			if(path.asString().equals("saveSurah.xapiKey")){
				return Mono.just(ResponseEntity.badRequest()
						.body(new ApiError("26", "Invalid header: ".concat("X-API-Key"))
						));
			}
		}
		String errorFields = e.getConstraintViolations()
				.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));

		return Mono.just(ResponseEntity.badRequest()
				.body(new ApiError("10", "Invalid Request. Error details : ".concat(errorFields))
				));
	}

}
