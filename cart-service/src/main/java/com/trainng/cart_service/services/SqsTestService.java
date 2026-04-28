package com.trainng.cart_service.services;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Service
public class SqsTestService {

    private final SqsAsyncClient sqsAsyncClient;

    public SqsTestService(SqsAsyncClient sqsAsyncClient) {
        this.sqsAsyncClient = sqsAsyncClient;
    }

    public void sendTestMessage() {
        sqsAsyncClient.sendMessage(
                builder -> builder
                        .queueUrl("http://localhost:4566/000000000000/add-cart-event")
                        .messageBody("{\"hello\":\"world\"}")
        );
    }
}