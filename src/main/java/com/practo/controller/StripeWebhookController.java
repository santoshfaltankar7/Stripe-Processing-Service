package com.practo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.practo.dto.stripe.StripeEventDto;
import com.practo.service.interfaces.StripeWebhookService;
import com.stripe.net.Webhook;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stripe/webhook")
@Slf4j
public class StripeWebhookController 
{
	@Value("${stripe.endpointSecret}")
	private String endpointSecret;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private StripeWebhookService service;

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
			e.printStackTrace();
			log.error("Invalid Signature");
			return ResponseEntity.badRequest().build();
		}
		
		StripeEventDto event=gson.fromJson(eventBody, StripeEventDto.class);
		log.info("Recieved Event Type"+event.getType());
		
		StripeEventDto eventDto=mapper.map(event, StripeEventDto.class);
    	
		service.processEvent(eventDto);
    	return ResponseEntity.ok().build();
    }
	
}
