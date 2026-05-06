package com.trainng.cart_service.services;

import org.springframework.stereotype.Service;

import com.trainng.cart_service.dto.event.CartEvent;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Service
public class CartEventProducerService {

    private final SqsTemplate sqsTemplate;

    public CartEventProducerService(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendCartEvent(CartEvent event, String queueName) {

        System.out.println(event.getEventType());

        sqsTemplate.send(to ->
                to.queue(queueName)
                .payload(event)
        );
    }
}