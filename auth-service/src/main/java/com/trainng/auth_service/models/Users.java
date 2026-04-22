package com.trainng.auth_service.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    
    @Id
    private UUID userId = UUID.randomUUID();

    private String email;
    private String password_hashed;
    private String username;
    private String role; //"CUSTOMER", "ADMIN", "MANAGER"

    private LocalDateTime createdAt = LocalDateTime.now();



}
