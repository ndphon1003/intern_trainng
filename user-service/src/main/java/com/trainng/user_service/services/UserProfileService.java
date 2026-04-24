package com.trainng.user_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trainng.user_service.dto.response.RolePatchResponse;
import com.trainng.user_service.dto.response.UploadAvatarResponse;
import com.trainng.user_service.dto.response.UserInformation;
import com.trainng.user_service.dto.response.UserListResponse;
import com.trainng.user_service.models.BusinessStatus;
import com.trainng.user_service.models.UserProfile;
import com.trainng.user_service.models.Users;
import com.trainng.user_service.repositories.UserProfileRepo;
import com.trainng.user_service.repositories.UserRepo;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepo userProfileRepo;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private BusinessStatusService businessStatusService;
    @Autowired
    private UserRepo userRepo;

    public UserProfile getProfileByUserId(UUID userId) {
        UserProfile userProfile = userProfileRepo.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUserId(userId);
            userProfileRepo.save(userProfile);
            businessStatusService.createBusinessStatusForUserId(userId);
        }
        return userProfile;
    }

    public UploadAvatarResponse uploadAvatar(UUID userId, MultipartFile avatar) throws Exception {
        String avatarUrl = cloudinaryService.uploadImage(avatar, userId);
        UserProfile userProfile = userProfileRepo.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUserId(userId);
        }
        userProfile.setAvatarUrl(avatarUrl);
        userProfileRepo.save(userProfile);
        return new UploadAvatarResponse(avatarUrl, userId);
    }
    
    public UserProfile updateUserProfile(UUID userId, String fullName, String bio, String phoneNumber,
            String address, String city, String country) {
        UserProfile userProfile = userProfileRepo.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUserId(userId);
        }
        if (fullName != null) {
            userProfile.setFullName(fullName);
        }
        if (bio != null) {
            userProfile.setBio(bio);
        }
        if (phoneNumber != null) {
            userProfile.setPhoneNumber(phoneNumber);
        }
        if (address != null) {
            userProfile.setAddress(address);
        }
        if (city != null) {
            userProfile.setCity(city);
        }
        if (country != null) {
            userProfile.setCountry(country);
        }
        return userProfileRepo.save(userProfile);
    }

    public UserListResponse getAllUserProfiles() {

        List<UserProfile> userProfiles = userProfileRepo.findAll();

        List<UserInformation> userInformations = new ArrayList<>();

        for (UserProfile user : userProfiles) {

            BusinessStatus status = businessStatusService.getBusinessStatusByUserId(user.getUserId());
            Users user_role = userRepo.findByUserId(user.getUserId());

            UserInformation info = new UserInformation();
            info.setUserProfile(user);
            info.setBusinessStatus(status);
            info.setRole(user_role.getRole());

            userInformations.add(info);
        }

        return new UserListResponse(userInformations.size(), userInformations);
    }

    public RolePatchResponse updateUserRole(UUID userId, String newRole) {
        Users user = userRepo.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setRole(newRole);
        userRepo.save(user);
        return new RolePatchResponse(userId, newRole);
    }
}


