package com.trainng.user_service.dto.response;

import com.trainng.user_service.models.BusinessStatus;
import com.trainng.user_service.models.UserProfile;

public class UserInformation {

    private UserProfile userProfile;
    private BusinessStatus businessStatus;

    public UserInformation() {
    }

    public UserInformation(UserProfile userProfile, BusinessStatus businessStatus) {
        this.userProfile = userProfile;
        this.businessStatus = businessStatus;
    }

    // ===== Getter / Setter =====

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public BusinessStatus getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(BusinessStatus businessStatus) {
        this.businessStatus = businessStatus;
    }
}