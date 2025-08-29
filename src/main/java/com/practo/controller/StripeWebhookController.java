package com.practo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.net.Webhook;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stripe/webhook")
@Slf4j
public class StripeWebhookController 
{
	@Value("${stripe.endpointSecret}")
	private String endpointSecret;

	@PostMapping
    public ResponseEntity<String> processStripeEvent(@RequestBody String eventBody,
    		@RequestHeader("stripe-signature") String signatureHeader)
    {
    	log.info(" \n \n \nRecieved Event :"+"eventBody :"+eventBody);
    	log.info("Recieved signature :"+"signatureHeader :"+signatureHeader);
    	
		try {
			Webhook.constructEvent(eventBody, signatureHeader, endpointSecret);
			log.info("Successfully verified webhook Signature");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Invalid Signature");
			return ResponseEntity.badRequest().build();
		}
    	
    	return ResponseEntity.ok().build();
    }
}
