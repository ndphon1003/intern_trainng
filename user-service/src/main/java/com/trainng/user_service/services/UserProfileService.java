package com.trainng.user_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainng.user_service.dto.request.RoleUpdateRequest;
import com.trainng.user_service.dto.response.AuthInfoResponse;
import com.trainng.user_service.dto.response.ResponseFormat;
import com.trainng.user_service.dto.response.RolePatchResponse;
import com.trainng.user_service.dto.response.UploadAvatarResponse;
import com.trainng.user_service.dto.response.UserInformation;
import com.trainng.user_service.dto.response.UserListResponse;
import com.trainng.user_service.models.UserProfile;
import com.trainng.user_service.repositories.UserProfileRepo;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepo userProfileRepo;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired RestTemplate restTemplate;

    public UserProfile getProfileByUserId(UUID userId) {
        UserProfile userProfile = userProfileRepo.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUserId(userId);
            userProfileRepo.save(userProfile);
        }
        return userProfile;
    }

    public UploadAvatarResponse uploadAvatar(UUID userId, MultipartFile avatar) throws Exception {
        String avatarUrl = cloudinaryService.uploadImage(avatar, userId);
        UserProfile userProfile = userProfileRepo.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUserId(userId);
        }
        userProfile.setAvatarUrl(avatarUrl);
        userProfileRepo.save(userProfile);
        return new UploadAvatarResponse(avatarUrl, userId);
    }
    
    public UserProfile updateUserProfile(UUID userId, String fullName, String bio, String phoneNumber,
            String address, String city, String country) {
        UserProfile userProfile = userProfileRepo.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUserId(userId);
        }
        if (fullName != null) {
            userProfile.setFullName(fullName);
        }
        if (bio != null) {
            userProfile.setBio(bio);
        }
        if (phoneNumber != null) {
            userProfile.setPhoneNumber(phoneNumber);
        }
        if (address != null) {
            userProfile.setAddress(address);
        }
        if (city != null) {
            userProfile.setCity(city);
        }
        if (country != null) {
            userProfile.setCountry(country);
        }
        return userProfileRepo.save(userProfile);
    }

    public UserListResponse getAllUserProfiles() {

        List<UserProfile> userProfiles = userProfileRepo.findAll();

        List<UserInformation> userInformations = new ArrayList<>();

        for (UserProfile user : userProfiles) {


            UserInformation info = new UserInformation();
            info.setUserProfile(user);

            userInformations.add(info);
        }

        return new UserListResponse(userInformations.size(), userInformations);
    }

    public RolePatchResponse updateUserRole(UUID userId, String newRole) {

        String url = "http://localhost:8081/api/auth/info?User-Id=" + userId;

        ResponseEntity<ResponseFormat> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ResponseFormat>() {}
                );

        ResponseFormat format = response.getBody();

        if (format == null || format.getData() == null) {
            throw new RuntimeException("Auth service returned null response");
        }

        ObjectMapper mapper = new ObjectMapper();

        AuthInfoResponse authInfo =
                mapper.convertValue(format.getData(), AuthInfoResponse.class);

        if (authInfo.isDeleted() || authInfo.isDeactivate()) {
            throw new RuntimeException("User is not active");
        }

        if (newRole.equals(authInfo.getRole())) {
            throw new RuntimeException("New role is the same as current role");
        }

        String roleUrl = "http://localhost:8081/api/auth/role";

        RoleUpdateRequest requestBody = new RoleUpdateRequest(userId, newRole);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RoleUpdateRequest> requestEntity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<AuthInfoResponse> patchResponse =
                restTemplate.exchange(
                        roleUrl,
                        HttpMethod.PATCH,
                        requestEntity,
                        AuthInfoResponse.class
                );

        AuthInfoResponse body = patchResponse.getBody();

        if (body == null) {
            throw new RuntimeException("Role update failed");
        }

        return new RolePatchResponse(userId, newRole);
    }
}


