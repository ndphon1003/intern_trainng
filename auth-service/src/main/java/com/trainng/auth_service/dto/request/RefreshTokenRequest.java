package com.trainng.auth_service.dto.request;

public class RefreshTokenRequest {
    private String refreshToken;

    public String getRefreshToken(){
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

}
