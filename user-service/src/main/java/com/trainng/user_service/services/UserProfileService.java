package com.trainng.user_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trainng.user_service.dto.response.UploadAvatarResponse;
import com.trainng.user_service.dto.response.UserInformation;
import com.trainng.user_service.dto.response.UserListResponse;
import com.trainng.user_service.models.UserProfile;
import com.trainng.user_service.repositories.UserProfileRepo;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepo userProfileRepo;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private BusinessStatusService businessStatusService;
    @Autowired
    private MongoTemplate mongoTemplate;

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

        Aggregation aggregation = Aggregation.newAggregation(

            // 1. JOIN business_statuses
            Aggregation.lookup(
                "business_statuses",
                "userId",
                "userId",
                "businessStatus"
            ),
            Aggregation.unwind("businessStatus", true),

            // 2. JOIN users (role nằm đây)
            Aggregation.lookup(
                "users",
                "userId",
                "_id",
                "userAuth"
            ),
            Aggregation.unwind("userAuth", true),

            // 3. PROJECT (QUAN TRỌNG)
            Aggregation.project()
                .and("userId").as("userProfile.userId")
                .and("fullName").as("userProfile.fullName")
                .and("businessStatus").as("businessStatus")
                .and("userAuth.role").as("role")
        );

        AggregationResults<UserInformation> results =
                mongoTemplate.aggregate(
                        aggregation,
                        "user_profiles", // ⚠️ check tên collection này
                        UserInformation.class
                );

        List<UserInformation> users = results.getMappedResults();

        return new UserListResponse(users.size(), users);
    }
}


