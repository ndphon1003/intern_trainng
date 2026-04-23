package com.trainng.auth_service.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "refresh_tokens")
public class RefreshToken {
    @Id
    private UUID tokenId;

    private String token;
    private UUID userId;
    private boolean isRevoked;
    private LocalDateTime expiryDateTime;   

    public RefreshToken() {
        this.tokenId = UUID.randomUUID();
        this.isRevoked = false;
        this.expiryDateTime = LocalDateTime.now().plusDays(7); // Default expiry time of 7 days
    }
    public RefreshToken(String token, UUID userId) {
        this.token = token;
        this.userId = userId;
        this.expiryDateTime = LocalDateTime.now().plusDays(7); // Default expiry time of 7 days
    }
    

    public UUID getTokenId() {
        return tokenId;
    }
    public void setTokenId(UUID tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public boolean isRevoked() {
        return isRevoked;
    }
    public void setRevoked(boolean isRevoked) {
        this.isRevoked = isRevoked;
    }
    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }
    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }
    
}
