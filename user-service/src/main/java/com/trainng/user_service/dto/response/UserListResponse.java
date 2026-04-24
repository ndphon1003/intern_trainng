package com.trainng.user_service.dto.response;

import java.util.List;

public class UserListResponse {

    private int totalUsers;
    private List<UserInformation> userInformations;

    public UserListResponse() {
    }

    public UserListResponse(int totalUsers, List<UserInformation> userInformations) {
        this.totalUsers = totalUsers;
        this.userInformations = userInformations;
    }

    // ===== Getter / Setter =====

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public List<UserInformation> getUserInformations() {
        return userInformations;
    }

    public void setUserInformations(List<UserInformation> userInformations) {
        this.userInformations = userInformations;
    }
}