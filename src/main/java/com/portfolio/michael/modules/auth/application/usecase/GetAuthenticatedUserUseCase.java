package com.portfolio.michael.modules.auth.application.usecase;

import com.portfolio.michael.modules.auth.application.dto.AuthResponse;
import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAuthenticatedUserUseCase {

    private final UserRepository userRepository;

    public AuthResponse execute(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        return AuthResponse.builder()
                .token(null) // Access token is already held by client
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toList()))
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
