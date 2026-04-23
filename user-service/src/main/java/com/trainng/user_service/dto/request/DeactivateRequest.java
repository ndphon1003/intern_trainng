package com.trainng.user_service.dto.request;

import java.util.UUID;

public class DeactivateRequest {
    private UUID userId;

    // ===== Constructor =====
    public DeactivateRequest() {
    }

    public DeactivateRequest(UUID userId) {
        this.userId = userId;
    }

    // ===== Getter =====
    public UUID getUserId() {
        return userId;
    }

    // ===== Setter =====
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}