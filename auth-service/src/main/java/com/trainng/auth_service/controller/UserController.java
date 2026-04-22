package com.trainng.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {
    

    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    @GetMapping("db-connection")
    public String checkDbConnection() {
        try {
            mongoTemplate.getDb().getName(); // Sẽ trả về tên database đang kết nối
            return "MongoDB connected successfully to database: " + mongoTemplate.getDb().getName();
        } catch (Exception e) {
            return "Failed to connect to MongoDB: " + e.getMessage();
        }
    }
}
