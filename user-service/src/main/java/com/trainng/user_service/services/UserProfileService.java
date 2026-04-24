package com.trainng.user_service.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trainng.user_service.dto.response.UploadAvatarResponse;
import com.trainng.user_service.models.UserProfile;
import com.trainng.user_service.repositories.UserProfileRepo;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepo userProfileRepo;
    @Autowired
    private CloudinaryService cloudinaryService;

    public UserProfile getProfileByUserId(UUID userId) {
        return userProfileRepo.findByUserId(userId);
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
}


