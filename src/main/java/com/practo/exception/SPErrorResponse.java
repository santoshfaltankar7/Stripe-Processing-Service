package com.practo.exception;

import lombok.Data;

@Data
public class SPErrorResponse 
{
	private String errorCode;
	private String errorMessage;

}
