package com.trainng.user_service.dto.request;

import java.util.UUID;

public class RolePatchRequest {

    private UUID userId;
    private String newRole;

    public RolePatchRequest() {
    }

    public RolePatchRequest(UUID userId, String newRole) {
        this.userId = userId;
        this.newRole = newRole;
    }

    // ===== Getter / Setter =====

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }
}