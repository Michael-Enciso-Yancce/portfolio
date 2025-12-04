package com.portfolio.michael.modules.auth.application.usecase;

import java.util.Set;

import com.portfolio.michael.modules.auth.application.dto.AuthResponse;
import com.portfolio.michael.modules.auth.application.dto.RegisterRequest;
import com.portfolio.michael.modules.auth.application.port.PasswordEncoderPort;
import com.portfolio.michael.modules.auth.application.port.TokenProvider;
import com.portfolio.michael.modules.auth.domain.Role;
import com.portfolio.michael.modules.auth.domain.RoleRepository;
import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;

public class RegisterUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoderPort passwordEncoder;

    public RegisterUseCase(UserRepository userRepository, RoleRepository roleRepository, TokenProvider tokenProvider,
            PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse execute(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .build();

        User savedUser = userRepository.save(user);

        String token = tokenProvider.generateToken(savedUser);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
