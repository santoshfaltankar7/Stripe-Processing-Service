package com.practo.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ProcessingException extends RuntimeException {
	private final String errorCode;
	private final String errorMessage;
	private final HttpStatus httpStatus;
	
	public ProcessingException(String errorCode, String errorMessage,HttpStatus httpStatus) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}


}