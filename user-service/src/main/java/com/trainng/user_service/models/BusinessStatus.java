package com.trainng.user_service.models;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "business_statuses")
public class BusinessStatus {

    @Id
    private UUID statusId;

    @Indexed(unique = true)
    private UUID userId;

    private boolean isDeactivated;
    private boolean isDeleted;
    private String role;

    public BusinessStatus() {
        this.statusId = UUID.randomUUID();
        this.isDeactivated = false;
        this.isDeleted = false;
    }

    public BusinessStatus(UUID userId) {
        this.statusId = UUID.randomUUID();
        this.userId = userId;
        this.isDeactivated = false;
        this.isDeleted = false;

    }

    // ===== Getter / Setter =====

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean isDeactivated() {
        return isDeactivated;
    }

    public void setDeactivated(boolean deactivated) {
        isDeactivated = deactivated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}