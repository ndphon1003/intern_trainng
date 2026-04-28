package com.trainng.user_service.dto.response;

import java.util.List;

import com.trainng.user_service.models.UserProfile;

public class UserListResponse {

    private int totalUsers;
    private List<UserProfile> userInformations;
    private List<AuthInfoResponse> authInfoResponses;

    public UserListResponse() {
    }

    public UserListResponse(int totalUsers, List<UserProfile> userInformations, List<AuthInfoResponse> authInfoResponses) {
        this.totalUsers = totalUsers;
        this.userInformations = userInformations;
        this.authInfoResponses = authInfoResponses;
    }

    // ===== Getter / Setter =====

    public List<AuthInfoResponse> getAuthInfoResponses(){
        return this.authInfoResponses;
    }

    public void setAuthInfoResponses(List<AuthInfoResponse> authInfoResponses){
        this.authInfoResponses = authInfoResponses;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public List<UserProfile> getUserInformations() {
        return userInformations;
    }

    public void setUserInformations(List<UserProfile> userInformations) {
        this.userInformations = userInformations;
    }
}