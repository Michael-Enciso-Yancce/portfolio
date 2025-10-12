package com.portfolio.michael.service.admin;

import com.portfolio.michael.dto.admin.request.AuthRequest;
import com.portfolio.michael.dto.admin.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}
