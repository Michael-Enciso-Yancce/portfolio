package com.portfolio.michael.modules.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.modules.auth.application.dto.AuthResponse;
import com.portfolio.michael.modules.auth.application.dto.LoginRequest;
import com.portfolio.michael.modules.auth.application.dto.RegisterRequest;
import com.portfolio.michael.modules.auth.application.usecase.LoginUseCase;
import com.portfolio.michael.modules.auth.application.usecase.RegisterUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUseCase.execute(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registerUseCase.execute(request));
    }
}
