package com.portfolio.michael.modules.auth.application.usecase;

import com.portfolio.michael.modules.auth.application.dto.AuthResponse;
import com.portfolio.michael.modules.auth.application.dto.LoginRequest;
import com.portfolio.michael.modules.auth.application.port.PasswordEncoderPort;
import com.portfolio.michael.modules.auth.application.port.TokenProvider;
import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;

public class LoginUseCase {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoderPort passwordEncoder;

    public LoginUseCase(UserRepository userRepository, TokenProvider tokenProvider,
            PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse execute(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenProvider.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(role -> role.getName())
                        .collect(java.util.stream.Collectors.toList()))
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
