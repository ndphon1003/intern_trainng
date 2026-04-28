package com.trainng.user_service.dto.response;

import com.trainng.user_service.models.UserProfile;

public class UserInfoResponse {
    private UserProfile userProfile;
    private AuthInfoResponse authInfoResponse;

    public UserInfoResponse() {
    }

    public UserInfoResponse(UserProfile userProfile, AuthInfoResponse authInfoResponse) {
        this.userProfile = userProfile;
        this.authInfoResponse = authInfoResponse;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public AuthInfoResponse getAuthInfoResponse() {
        return authInfoResponse;
    }

    public void setAuthInfoResponse(AuthInfoResponse authInfoResponse) {
        this.authInfoResponse = authInfoResponse;
    }
}