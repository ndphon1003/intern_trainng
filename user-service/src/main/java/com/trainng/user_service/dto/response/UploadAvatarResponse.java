package com.trainng.user_service.dto.response;

import java.util.UUID;

public class UploadAvatarResponse {
    private String avatarUrl;
    private UUID userId;
    
    public UploadAvatarResponse() {
    }
    public UploadAvatarResponse(String avatarUrl, UUID userId) {
        this.avatarUrl = avatarUrl;
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
