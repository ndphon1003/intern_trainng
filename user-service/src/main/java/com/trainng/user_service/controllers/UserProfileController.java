package com.trainng.user_service.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trainng.user_service.dto.request.DeactivateRequest;
import com.trainng.user_service.dto.request.RolePatchRequest;
import com.trainng.user_service.dto.request.UpdateProfileRequest;
import com.trainng.user_service.dto.response.ResponseFormat;
import com.trainng.user_service.dto.response.UploadAvatarResponse;
import com.trainng.user_service.middlewares.RoleValidate;
import com.trainng.user_service.middlewares.ValidateResponse;
import com.trainng.user_service.models.BusinessStatus;
import com.trainng.user_service.models.UserProfile;
import com.trainng.user_service.services.BusinessStatusService;
import com.trainng.user_service.services.UserProfileService;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
    
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private BusinessStatusService businessStatusService;

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

    @PatchMapping("/update-profile")
    public ResponseEntity<ResponseFormat> updateUserProfile(@RequestBody UpdateProfileRequest request,
            @RequestHeader("X-User-Id") String userId){
        try {
            
            UserProfile profile = userProfileService.updateUserProfile(UUID.fromString(userId), request.getFullName(), request.getBio(), request.getPhoneNumber(), request.getAddress(), request.getCity(), request.getCountry());
            return ResponseEntity.ok(new ResponseFormat(200, "Profile updated successfully", profile));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseFormat(500, "Failed to update profile: " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseFormat> getAllUsers() {
        try {
            var response = userProfileService.getAllUserProfiles();

            return ResponseEntity.ok(
                    new ResponseFormat(200, "Users retrieved successfully", response)
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ResponseFormat(500, "Failed to get users: " + e.getMessage(), null)
            );
        }
    }

    @PatchMapping("/update-role")
    public ResponseEntity<ResponseFormat> updateUserRole(@RequestBody RolePatchRequest request) {

        try {
            ValidateResponse validate = RoleValidate.validateRole(request.getNewRole());

            if (validate.getCode() != 200) {
                return ResponseEntity
                        .badRequest()
                        .body(new ResponseFormat(
                                validate.getCode(),
                                validate.getMessage(),
                                null
                        ));
            }

            var response = userProfileService.updateUserRole(
                    request.getUserId(),
                    request.getNewRole()
            );

            return ResponseEntity.ok(
                    new ResponseFormat(200, "Updated role successfully", response)
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(500)
                    .body(new ResponseFormat(
                            500,
                            "Failed to update role: " + e.getMessage(),
                            null
                    ));
        }
    }
    
    @PatchMapping("/deactivate-user")
    public ResponseEntity<ResponseFormat> deactivateUser(@RequestBody DeactivateRequest request) {

        try {
            BusinessStatus result = businessStatusService.deactivateUser(request.getUserId());

            return ResponseEntity.ok(
                new ResponseFormat(200, "User deactivated successfully", result)
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                new ResponseFormat(500, "Failed to deactivate user: " + e.getMessage(), null)
            );
        }
    }
}
