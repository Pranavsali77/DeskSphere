package com.ithelpdesk.backend.service;
import com.ithelpdesk.backend.dto.*;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String changePassword(ChangePasswordRequest request, String email);

}