package dev.cmlee.cosmosapi.exception.handler;

import dev.cmlee.cosmosapi.exception.BasicErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BasicErrorResponse> processValidationError(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();

		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			builder.append(fieldError.getField());
			builder.append(" ");
			builder.append(fieldError.getDefaultMessage());
		}

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new BasicErrorResponse(builder.toString()));
	}
}
