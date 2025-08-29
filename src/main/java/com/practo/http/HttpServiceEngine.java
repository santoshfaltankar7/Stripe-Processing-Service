package com.practo.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.practo.constant.ErrorCodeEnum;
import com.practo.exception.ProcessingException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpServiceEngine 
{

	private RestClient restClient;
	public HttpServiceEngine(RestClient.Builder restClientBuilder) 
	{
		this.restClient=restClientBuilder.build();
	}

	
	@CircuitBreaker(name = "Stripe_Processing", fallbackMethod = "fallbackProcessPayment")
	public ResponseEntity<String> makeHttpCall(HttpRequest httpRequest)

	{
		log.info("invoked httpCall || httpRequest"+httpRequest);


		try {


			ResponseEntity<String>response= restClient.method(httpRequest.getMethod())
					.uri(httpRequest.getUrl())
					.headers(headers ->{
						headers.addAll(httpRequest.getHeaders());
					})
					.body(httpRequest.getRequestBody())
					.retrieve()
					.toEntity(String.class);

			log.info("response"+response);
			return response;
		}catch(HttpClientErrorException | HttpServerErrorException e)
		{

			log.error("HttpClientErrorException occured",e);

			if(e.getStatusCode().equals(HttpStatus.GATEWAY_TIMEOUT)||e.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE))
			{
				log.error("received from 5xx statuscode"+e.getStatusCode(),e);
				throw new ProcessingException(ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorCode(),ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorMessage(),HttpStatus.valueOf(e.getStatusCode().value()));


			}
			log.info("Got getResponseBodyAsString"+e.getResponseBodyAsString());
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());

		}
		catch (Exception e2) {
	        System.out.println("General exception occurred"+e2);
            throw new ProcessingException(ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorCode(),ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	}

	public ResponseEntity<String> fallbackProcessPayment(HttpRequest httpRequest, Throwable t) {
		// Handle fallback logic here
		log.error("Fallback method invoked due to exception:" + t.getMessage());
		throw new ProcessingException(
				ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorCode(),
				ErrorCodeEnum.UNABLE_TO_CONNECT_TO_STRIPE_PS.getErrorMessage(),
				HttpStatus.SERVICE_UNAVAILABLE);
	}

}
