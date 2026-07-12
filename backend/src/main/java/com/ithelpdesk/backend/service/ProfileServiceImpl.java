package com.ithelpdesk.backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ithelpdesk.backend.dto.ChangePasswordRequest;
import com.ithelpdesk.backend.dto.ProfileResponse;
import com.ithelpdesk.backend.dto.UpdateProfileRequest;
import com.ithelpdesk.backend.entity.User;
import com.ithelpdesk.backend.repository.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ProfileResponse getMyProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponse response = new ProfileResponse();

        response.setId(user.getId());
        response.setName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole().getRoleName().name());
        response.setDepartment(user.getDepartment().getDepartmentName());
        return response;
    }

    @Override
    public ProfileResponse updateProfile(Long userId,
                                         UpdateProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(request.getName());

        user.setPhone(request.getPhone());

        user = userRepository.save(user);

        ProfileResponse response = new ProfileResponse();

        response.setId(user.getId());
        response.setName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole().getRoleName().name());
        response.setDepartment(user.getDepartment().getDepartmentName());

        return response;
    }

    @Override
    public void changePassword(Long userId,
                               ChangePasswordRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

}