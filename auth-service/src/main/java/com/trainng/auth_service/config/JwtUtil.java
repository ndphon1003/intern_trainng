package com.trainng.auth_service.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    
    private JwtConfig jwtConfig;
    private SecretKey secretKey;

    public JwtUtil(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    public String generateToken(String username, String role, boolean isRefreshToken) {
        // Implement JWT token generation logic using jwtConfig and secretKey
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (isRefreshToken ? jwtConfig.getRefreshExpiration() : jwtConfig.getAccessExpiration())))
                .signWith(secretKey)
                .compact();
    }
}
