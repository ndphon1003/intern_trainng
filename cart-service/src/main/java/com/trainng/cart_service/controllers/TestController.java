package com.trainng.cart_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainng.cart_service.services.SqsTestService;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SqsTestService sqsTestService;

    @GetMapping("/sqs")
    public String test() {
        sqsTestService.sendTestMessage();
        return "sent";
    }
}