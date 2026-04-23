package com.trainng.user_service.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trainng.user_service.dto.response.ResponseFormat;
import com.trainng.user_service.dto.response.UploadAvatarResponse;
import com.trainng.user_service.services.UserProfileService;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
    
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/profile")
    public ResponseEntity<ResponseFormat> getUserProfile(@RequestHeader("X-User-Id") String userId) {
        var profile = userProfileService.getProfileByUserId(UUID.fromString(userId));
        if (profile == null) {
            return ResponseEntity.status(404).body(new ResponseFormat(404, "User profile not found", null));
        }
        return ResponseEntity.ok(new ResponseFormat(200, "User profile retrieved successfully", profile));
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<ResponseFormat> uploadUserProfile(@RequestHeader("X-User-Id") String userId, 
                @RequestParam MultipartFile avatar){
        try {
            UploadAvatarResponse response = userProfileService.uploadAvatar(UUID.fromString(userId), avatar);
            return ResponseEntity.ok(new ResponseFormat(200, "Avatar uploaded successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseFormat(500, "Failed to upload avatar: " + e.getMessage(), null));
        }
    }
}
