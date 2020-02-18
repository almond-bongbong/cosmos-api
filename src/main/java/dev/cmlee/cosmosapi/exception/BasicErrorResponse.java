package dev.cmlee.cosmosapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class BasicErrorResponse {

	private String message;
}
