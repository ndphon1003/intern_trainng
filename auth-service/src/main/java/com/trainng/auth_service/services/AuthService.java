package com.trainng.auth_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainng.auth_service.config.JwtUtil;
import com.trainng.auth_service.dto.response.AuthResponse;
import com.trainng.auth_service.models.Users;
import com.trainng.auth_service.repositories.UserRepo;

@Service
public class AuthService {
    
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthResponse register(String username, String email, String password, String role) {
        // encoder password
        String encodedPassword = passwordEncoder.encode(password);
        // save user to database
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword_hashed(encodedPassword);
        user.setRole(role);

        String accessToken = jwtUtil.generateToken(username, role, false);
        String refreshToken = jwtUtil.generateToken(username, role, true);

        userRepo.save(user);

        return new AuthResponse(accessToken, refreshToken, user.getUserId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public String generateToken(String username, String role, boolean isRefreshToken) {
        return jwtUtil.generateToken(username, role, isRefreshToken);
    }

    public AuthResponse login(String username, String password) {
        Users user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }

        if (!passwordEncoder.matches(password, user.getPassword_hashed())) {
            return null;
        }

        String accessToken = jwtUtil.generateToken(username, user.getRole(), false);
        String refreshToken = jwtUtil.generateToken(username, user.getRole(), true);

        return new AuthResponse(accessToken, refreshToken, user.getUserId(), user.getUsername(), user.getEmail(), user.getRole());
    }

}
