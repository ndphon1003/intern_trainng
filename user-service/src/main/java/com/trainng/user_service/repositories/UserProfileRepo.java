package com.trainng.user_service.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainng.user_service.models.UserProfile;

@Repository
public interface  UserProfileRepo extends MongoRepository<UserProfile, UUID>{
    UserProfile findByUserId(UUID userId);
    
}
