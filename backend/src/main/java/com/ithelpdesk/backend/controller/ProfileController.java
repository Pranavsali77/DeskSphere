package com.ithelpdesk.backend.controller;

import com.ithelpdesk.backend.dto.ChangePasswordRequest;
import com.ithelpdesk.backend.dto.ProfileResponse;
import com.ithelpdesk.backend.dto.UpdateProfileRequest;
import com.ithelpdesk.backend.service.ProfileService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getMyProfile(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable Long userId,
            @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(profileService.updateProfile(userId, request));
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long userId,
            @RequestBody ChangePasswordRequest request) {

        profileService.changePassword(userId, request);
        return ResponseEntity.ok("Password changed successfully.");
    }
}