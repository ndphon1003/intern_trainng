package com.trainng.user_service.dto.response;

import com.trainng.user_service.models.UserProfile;

public class UserInformation {

    private UserProfile userProfile;
    private String role;
    private boolean isDeactivated;
    private boolean isDeleted;
    private String username;

    // ===== Constructor =====

    // Constructor
    public UserInformation() {
    }

    // Constructor
    public UserInformation(UserProfile userProfile, String role) {
        this.userProfile = userProfile;
        this.role = role;
    }

    // Constructor
    public UserInformation(UserProfile userProfile, String role, boolean isDeactivated, boolean isDeleted, String username) {
        this.userProfile = userProfile;
        this.role = role;
        this.isDeactivated = isDeactivated;
        this.isDeleted = isDeleted;
        this.username = username;
    }

    // ===== Getter / Setter =====

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // ===== toString Method =====

    @Override
    public String toString() {
        return "UserInformation{" +
                "userProfile=" + userProfile +
                ", role='" + role + '\'' +
                ", isDeactivated=" + isDeactivated +
                ", isDeleted=" + isDeleted +
                ", username='" + username + '\'' +
                '}';
    }
}