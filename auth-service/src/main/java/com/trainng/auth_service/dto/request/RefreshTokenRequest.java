package com.trainng.auth_service.dto.request;

public class RefreshTokenRequest {
    private String refreshToken;
    private String username;

    public String getRefreshToken(){
        return refreshToken;
    }

    public String getUsername(){
        return username;

    }
    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
    public void setUsername(String username){
        this.username = username;
    }
}
