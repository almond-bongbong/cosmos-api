package dev.cmlee.cosmosapi.exception.handler;

import dev.cmlee.cosmosapi.exception.BasicErrorResponse;
import dev.cmlee.cosmosapi.exception.RequiredParameterException;
import dev.cmlee.cosmosapi.exception.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(value = UnAuthorizedException.class)
	public ResponseEntity<BasicErrorResponse> unAuthorizedHandler() {
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(new BasicErrorResponse("유효하지 않은 토큰입니다."));
	}

	@ExceptionHandler(value = RequiredParameterException.class)
	public ResponseEntity<BasicErrorResponse> requiredParameterHandler(RequiredParameterException e) {
		return ResponseEntity
				.badRequest()
				.body(new BasicErrorResponse(e.getMessage()));
	}
}
