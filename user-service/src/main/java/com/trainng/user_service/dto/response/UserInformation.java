package com.trainng.user_service.dto.response;

import com.trainng.user_service.models.UserProfile;

public class UserInformation {
    private AuthInfoResponse authInfoResponse;
    private UserProfile userProfile;

    public UserInformation() {
    }

    public UserInformation(AuthInfoResponse authInfoResponse, UserProfile userProfile) {
        this.authInfoResponse = authInfoResponse;
        this.userProfile = userProfile;
    }

    public AuthInfoResponse getAuthInfoResponse() {
        return authInfoResponse;
    }

    public void setAuthInfoResponse(AuthInfoResponse authInfoResponse) {
        this.authInfoResponse = authInfoResponse;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}