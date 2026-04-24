package com.trainng.user_service.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Users {
    
    @Id
    private UUID userId;

    private String email;
    private String password_hashed;
    @Indexed(unique = true)
    private String username;
    private String role; //"CUSTOMER", "ADMIN", "MANAGER"

    private LocalDateTime createdAt;

    public Users() {
    }
    public Users(UUID userId, String email, String password_hashed, String username, String role) {
        this.userId = userId;
        this.email = email;
        this.password_hashed = password_hashed;
        this.username = username;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword_hashed() {
        return password_hashed;
    }
    public void setPassword_hashed(String password_hashed) {
        this.password_hashed = password_hashed;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



}
