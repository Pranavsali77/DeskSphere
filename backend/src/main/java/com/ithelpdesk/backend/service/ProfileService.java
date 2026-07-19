package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.ChangePasswordRequest;
import com.ithelpdesk.backend.dto.ProfileResponse;
import com.ithelpdesk.backend.dto.UpdateProfileRequest;

public interface ProfileService {

    ProfileResponse getMyProfile(Long userId);

    ProfileResponse updateProfile(Long userId, UpdateProfileRequest request);

    void changePassword(Long userId, ChangePasswordRequest request);
}