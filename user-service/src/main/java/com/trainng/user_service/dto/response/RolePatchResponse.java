package com.trainng.user_service.dto.response;

import java.util.UUID;

public class RolePatchResponse {

    private UUID userId;
    private String role;

    public RolePatchResponse() {
    }

    public RolePatchResponse(UUID userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    // ===== Getter / Setter =====

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}