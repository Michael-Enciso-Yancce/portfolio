package com.portfolio.michael.service.admin;

import com.portfolio.michael.dto.admin.request.LoginRequest;
import com.portfolio.michael.dto.admin.request.RegisterRequest;
import com.portfolio.michael.dto.admin.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
