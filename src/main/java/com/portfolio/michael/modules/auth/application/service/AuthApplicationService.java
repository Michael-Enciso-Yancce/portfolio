package com.portfolio.michael.modules.auth.application.service;

import com.portfolio.michael.modules.auth.application.dto.AuthResponse;
import com.portfolio.michael.modules.auth.application.dto.LoginRequest;
import com.portfolio.michael.modules.auth.application.dto.RegisterRequest;
import com.portfolio.michael.modules.auth.application.dto.UpdateProfileRequest;
import com.portfolio.michael.modules.auth.application.usecase.GetAuthenticatedUserUseCase;
import com.portfolio.michael.modules.auth.application.usecase.LoginUseCase;
import com.portfolio.michael.modules.auth.application.usecase.RegisterUseCase;
import com.portfolio.michael.modules.auth.application.usecase.UpdateProfileUseCase;
import com.portfolio.michael.modules.file.domain.model.FileInput;

public class AuthApplicationService {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;

    public AuthApplicationService(
            LoginUseCase loginUseCase,
            RegisterUseCase registerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            UpdateProfileUseCase updateProfileUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.updateProfileUseCase = updateProfileUseCase;
    }

    public AuthResponse login(LoginRequest request) {
        return loginUseCase.execute(request);
    }

    public AuthResponse register(RegisterRequest request) {
        return registerUseCase.execute(request);
    }

    public AuthResponse getCurrentUser(String email) {
        return getAuthenticatedUserUseCase.execute(email);
    }

    public AuthResponse updateProfile(String email, UpdateProfileRequest request, FileInput profileImage) {
        return updateProfileUseCase.execute(email, request, profileImage);
    }
}
