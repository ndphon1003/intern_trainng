package com.trainng.auth_service.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;


@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access.expiration}")
    private long access_expiration;
    @Value("${jwt.refresh.expiration}")
    private long refresh_expiration;

    public String getSecret() {
        return secret;
    }
    public long getAccessExpiration() {
        return access_expiration;
    }
    public long getRefreshExpiration() {
        return refresh_expiration;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public void setAccessExpiration(long access_expiration) {
        this.access_expiration = access_expiration;
    }
    public void setRefreshExpiration(long refresh_expiration) {
        this.refresh_expiration = refresh_expiration;
    }

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
