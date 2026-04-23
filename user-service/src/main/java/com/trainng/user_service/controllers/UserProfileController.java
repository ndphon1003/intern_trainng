package com.trainng.user_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainng.user_service.dto.response.ResponseFormat;
import com.trainng.user_service.services.UserProfileService;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
    
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/profile")
    public ResponseEntity<ResponseFormat> getUserProfile(@RequestHeader("X-User-Id") String userId) {
        var profile = userProfileService.getProfileByUserId(java.util.UUID.fromString(userId));
        if (profile == null) {
            return ResponseEntity.status(404).body(new ResponseFormat(404, "User profile not found", null));
        }
        return ResponseEntity.ok(new ResponseFormat(200, "User profile retrieved successfully", profile));
    }


}
