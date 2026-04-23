package com.trainng.auth_service.config;

import java.util.Date;
import java.util.UUID;

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

    public String generateToken(UUID userId, String username, String role, boolean isRefreshToken) {
        // Implement JWT token generation logic using jwtConfig and secretKey
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (isRefreshToken ? jwtConfig.getRefreshExpiration() : jwtConfig.getAccessExpiration())))
                .signWith(secretKey)
                .compact();
    }
    public String extractUsername(String token) {
        // Implement logic to extract username from JWT token
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public UUID extractUserId(String token) {
        // Implement logic to extract user ID from JWT token
        return UUID.fromString(Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class));
    }
}
