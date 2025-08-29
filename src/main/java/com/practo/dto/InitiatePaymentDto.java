package com.practo.dto;

import java.util.List;

import com.practo.pojo.Item;

import lombok.Data;

@Data
public class InitiatePaymentDto 
{
	private String successUrl;
	private String cancelUrl;
	
	private List<Item>lineItem;

}
