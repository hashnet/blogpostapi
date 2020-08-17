package com.hashdroid.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestParamValueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidRequestParamValueException(String name, String receivedValue) {
		super("Invalid value: " + receivedValue + " received for Request Parameter: " + name + ".");
	}

	public InvalidRequestParamValueException(String name, String receivedValue, String expectedValue) {
		super("Invalid value: [" + receivedValue + "] received for Request Parameter: [" + name + "]. Expected value: "
				+ expectedValue + ".");
	}
}
