package com.portfolio.michael.modules.auth.service;

import com.portfolio.michael.modules.auth.dto.AuthResponse;
import com.portfolio.michael.modules.auth.dto.LoginRequest;
import com.portfolio.michael.modules.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
