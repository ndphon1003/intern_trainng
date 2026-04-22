package com.trainng.auth_service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainng.auth_service.models.RefreshToken;

public interface RefreshTokenRepo extends MongoRepository<RefreshToken, UUID>{

    // tìm theo token string
    Optional<RefreshToken> findByToken(String token);

    // tìm theo userId (rất hay dùng)
    Optional<RefreshToken> findByUserId(UUID userId);

    // xoá theo userId (logout all devices)
    void deleteByUserId(UUID userId);

    // xoá theo token
    void deleteByToken(String token);

}
