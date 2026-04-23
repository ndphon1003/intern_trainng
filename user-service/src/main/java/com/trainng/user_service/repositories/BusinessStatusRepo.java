package com.trainng.user_service.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainng.user_service.models.BusinessStatus;

@Repository
public interface BusinessStatusRepo extends MongoRepository<BusinessStatus, UUID> {
    BusinessStatus findByUserId(UUID userId);
}
