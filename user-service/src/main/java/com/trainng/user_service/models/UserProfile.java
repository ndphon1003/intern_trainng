package com.trainng.user_service.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private UUID profileId;

    @Indexed(unique = true)
    private UUID userId; // tham chiếu đến Users.userId bên auth-service

    // Thông tin cá nhân
    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
    private String bio;

    // Địa chỉ
    private String address;
    private String city;
    private String country;

    private LocalDateTime updatedAt;

    public UserProfile() {
        this.profileId = UUID.randomUUID();
        this.updatedAt = LocalDateTime.now();
    }

    public UserProfile(UUID userId) {
        this.profileId = UUID.randomUUID();
        this.userId = userId;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getProfileId() { return profileId; }
    public void setProfileId(UUID profileId) { this.profileId = profileId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}