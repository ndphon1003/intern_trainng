package com.trainng.auth_service.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainng.auth_service.config.JwtUtil;
import com.trainng.auth_service.dto.response.AuthInfoResponse;
import com.trainng.auth_service.dto.response.AuthResponse;
import com.trainng.auth_service.models.RefreshToken;
import com.trainng.auth_service.models.Users;
import com.trainng.auth_service.repositories.RefreshTokenRepo;
import com.trainng.auth_service.repositories.UserRepo;

@Service
public class AuthService {
    
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    
    public AuthResponse register(String username, String email, String password, String role) {
        // encoder password
        String encodedPassword = passwordEncoder.encode(password);
        // save user to database
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword_hashed(encodedPassword);
        user.setRole(role);

        String accessToken = jwtUtil.generateToken(user.getUserId(), username, role, false);
        String refreshToken = jwtUtil.generateToken(user.getUserId(), username, role, true);

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUserId(user.getUserId());
        
        userRepo.save(user);
        refreshTokenRepo.save(refreshTokenEntity);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(accessToken);
        authResponse.setRefreshToken(refreshToken);
        authResponse.setUserId(user.getUserId());
        authResponse.setUsername(user.getUsername());
        authResponse.setEmail(user.getEmail());
        authResponse.setRole(user.getRole());

        return authResponse;
    }

    public String generateToken(UUID userId, String username, String role, boolean isRefreshToken) {
        return jwtUtil.generateToken(userId, username, role, isRefreshToken);
    }

    public AuthResponse login(String username, String password) {
        Users user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }

        if (!passwordEncoder.matches(password, user.getPassword_hashed())) {
            return null;
        }

        if (user.isDeactivate() || user.isDeleted()){
            return null;
        }

        String accessToken = jwtUtil.generateToken(user.getUserId(), username, user.getRole(), false);
        String refreshToken = jwtUtil.generateToken(user.getUserId(),username, user.getRole(), true);

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUserId(user.getUserId());
        refreshTokenRepo.save(refreshTokenEntity);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(accessToken);
        authResponse.setRefreshToken(refreshToken);
        authResponse.setUserId(user.getUserId());
        authResponse.setUsername(user.getUsername());
        authResponse.setEmail(user.getEmail());
        authResponse.setRole(user.getRole());

        return authResponse;
    }

    public boolean logout(String refreshToken) {

        var tokenOpt = refreshTokenRepo.findByToken(refreshToken);

        if (tokenOpt.isEmpty()) {
            return false;
        }

        var token = tokenOpt.get();

        if (token.isRevoked()) {
            return false;
        }

        refreshTokenRepo.delete(token);

        return true;
    }

    public AuthResponse refreshToken(String refreshToken) {
        var refreshTokenOpt = refreshTokenRepo.findByToken(refreshToken);
        if (refreshTokenOpt.isEmpty()) {
            return null;
        }

        var tokenEntity = refreshTokenOpt.get();
        if (tokenEntity.isRevoked()) {
            return null;
        }

        String username = jwtUtil.extractUsername(refreshToken);
        Users user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        String role = user.getRole();

        String newAccessToken = jwtUtil.generateToken(user.getUserId(), username, role, false);

        String newRefreshToken = jwtUtil.generateToken(user.getUserId(), username, role, true);
        tokenEntity.setRevoked(true);
        refreshTokenRepo.save(tokenEntity);

        RefreshToken newRefreshTokenEntity = new RefreshToken();
        newRefreshTokenEntity.setToken(newRefreshToken);
        newRefreshTokenEntity.setUserId(tokenEntity.getUserId());
        refreshTokenRepo.save(newRefreshTokenEntity);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(newAccessToken);
        authResponse.setRefreshToken(newRefreshToken);
        authResponse.setUserId(user.getUserId());
        authResponse.setUsername(user.getUsername());
        authResponse.setEmail(user.getEmail());
        authResponse.setRole(user.getRole());
        return authResponse;
    }

    public AuthInfoResponse getAuthInfor(UUID userId){
        Users user = userRepo.findByUserId(userId).orElse(null);
        if (user == null){
            return null;
        }

        AuthInfoResponse response = new AuthInfoResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setEmail(user.getEmail());
        response.setDeactivate(user.isDeactivate());
        response.setDeleted(user.isDeleted());

        return response;

    }

    public AuthInfoResponse updateUserRole(UUID userId, String role) {

        Users user = userRepo.findByUserId(userId).orElse(null);

        if (user == null) {
            return null;
        }

        if (user.isDeleted() || user.isDeactivate()) {
            return null;
        }

        // ===== VALIDATE ROLE =====
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }

        String normalizedRole = role.toUpperCase();

        if (!normalizedRole.equals("CUSTOMER")
                && !normalizedRole.equals("ADMIN")
                && !normalizedRole.equals("MANAGER")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        // ===== UPDATE ROLE =====
        user.setRole(normalizedRole);
        userRepo.save(user);

        // ===== RESPONSE =====
        AuthInfoResponse response = new AuthInfoResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setEmail(user.getEmail());
        response.setDeactivate(user.isDeactivate());
        response.setDeleted(user.isDeleted());

        return response;
    }
}
