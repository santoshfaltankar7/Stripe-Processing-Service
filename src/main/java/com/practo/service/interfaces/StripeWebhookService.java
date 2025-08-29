package com.practo.service.interfaces;

import com.practo.dto.stripe.StripeEventDto;

public interface StripeWebhookService 
{
public void processEvent(StripeEventDto eventDto);
}
