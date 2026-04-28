package com.trainng.user_service.dto.response;

public class AuthInfoResponse {
    private String username;
    private String role;
    private String email;
    private boolean isDeactivate;
    private boolean isDeleted;

    public AuthInfoResponse() {
    }

    public AuthInfoResponse(String username, String role, String email, boolean isDeactivate, boolean isDeleted) {
        this.username = username;
        this.role = role;
        this.email = email;
        this.isDeactivate = isDeactivate;
        this.isDeleted = isDeleted;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeactivate() {
        return isDeactivate;
    }

    public void setDeactivate(boolean isDeactivate) {
        this.isDeactivate = isDeactivate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}