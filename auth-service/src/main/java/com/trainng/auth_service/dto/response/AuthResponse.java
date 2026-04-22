package com.trainng.auth_service.dto.response;

import java.util.UUID;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UUID userId;
    private String username;
    private String email;
    private String role;

    public AuthResponse(String accessToken, String refreshToken, UUID userId, String username, String email, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }   
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
}
