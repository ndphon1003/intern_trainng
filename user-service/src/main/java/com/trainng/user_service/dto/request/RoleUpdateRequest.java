package com.trainng.user_service.dto.request;

import java.util.UUID;

public class RoleUpdateRequest {
    private UUID userId;
    private String role;

    public RoleUpdateRequest() {
    }

    public RoleUpdateRequest(UUID userId, String role) {
        this.userId = userId;
        this.role = role;
    }

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