package com.trainng.user_service.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainng.user_service.models.Users;

@Repository
public interface UserRepo extends MongoRepository<Users, UUID> {
    Users findByUserId(UUID userId);
    
}
