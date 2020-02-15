package dev.cmlee.cosmosapi.exception;

public class RequiredParameterException extends RuntimeException {

	public RequiredParameterException(String message) {
		super(message);
	}
}
