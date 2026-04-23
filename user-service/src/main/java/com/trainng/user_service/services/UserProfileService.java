package com.trainng.user_service.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainng.user_service.models.UserProfile;
import com.trainng.user_service.repositories.UserProfileRepo;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepo userProfileRepo;

    public UserProfile getProfileByUserId(UUID userId) {
        return userProfileRepo.findByUserId(userId);
    }
}
