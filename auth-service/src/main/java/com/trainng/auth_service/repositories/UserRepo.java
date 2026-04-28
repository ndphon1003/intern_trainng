package com.trainng.auth_service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainng.auth_service.models.Users;

@Repository
public interface UserRepo extends  MongoRepository<Users, UUID>{
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByUserId(UUID userId);
}
