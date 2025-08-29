package com.practo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException 
{
	@ExceptionHandler(ProcessingException.class)
	public ResponseEntity<ErrorRes> handleStripeProviderException(ProcessingException ex){
		System.out.println("Stripe provider exception occured:"+ex);
		ErrorRes errorRes=new ErrorRes();
		errorRes.setErrorCode(ex.getErrorCode());
		errorRes.setErrorMessage(ex.getErrorMessage());
		System.out.println("In GlobalExceptionHandler Returning errorRes:"+errorRes);
		return new ResponseEntity<ErrorRes>(errorRes,ex.getHttpStatus());
	}

}


