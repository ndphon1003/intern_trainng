package com.trainng.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainng.auth_service.dto.request.LoginRequest;
import com.trainng.auth_service.dto.request.LogoutRequest;
import com.trainng.auth_service.dto.request.RefreshTokenRequest;
import com.trainng.auth_service.dto.request.RegisterRequest;
import com.trainng.auth_service.dto.response.AuthResponse;
import com.trainng.auth_service.dto.response.ResponseFormat;
import com.trainng.auth_service.middlewares.RegisterValidate;
import com.trainng.auth_service.middlewares.ValidateResponse;
import com.trainng.auth_service.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    @Autowired
    private RegisterValidate registerValidate;



    @PostMapping("/register")
    public ResponseEntity<ResponseFormat> register(@RequestBody RegisterRequest request) {
        //Validate input
        ValidateResponse validateResponse = registerValidate.validate(request.getUsername(), request.getEmail(), request.getPassword(), request.getRole());
        if (validateResponse.getCode() != 200) {
            return ResponseEntity.status(validateResponse.getCode()).body(new ResponseFormat(validateResponse.getCode(), validateResponse.getMessage(), null));
        }
        
        AuthResponse response = authService.register(request.getUsername(), request.getEmail(), request.getPassword(), request.getRole());
        return ResponseEntity.ok(new ResponseFormat(validateResponse.getCode(), validateResponse.getMessage(), response));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseFormat> login(@RequestBody LoginRequest request) {
        //Validate input
        AuthResponse response = authService.login(request.getUsername(), request.getPassword());
    
        if (response == null) {
            return ResponseEntity.status(400).body(new ResponseFormat(400, "Invalid username or password", null));
        }
        return ResponseEntity.ok(new ResponseFormat(200, "User logged in successfully", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseFormat> logout(@RequestBody LogoutRequest request) {
        boolean isRevoked = authService.logout(request.getRefreshToken());
        if (!isRevoked) {
            return ResponseEntity.status(400).body(new ResponseFormat(400, "Invalid refresh token", null));
        }
        return ResponseEntity.ok(new ResponseFormat(200, "User logged out successfully", null));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseFormat> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        AuthResponse response = authService.refreshToken(refreshToken.getRefreshToken());
        if (response == null) {
            return ResponseEntity.status(400).body(new ResponseFormat(400, "Invalid refresh token", null));
        }
        return ResponseEntity.ok(new ResponseFormat(200, "Token refreshed successfully", response));
    }

}
