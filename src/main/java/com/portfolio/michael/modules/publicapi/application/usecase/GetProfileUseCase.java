package com.portfolio.michael.modules.publicapi.application.usecase;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.publicapi.application.dto.ProfileResponse;

public class GetProfileUseCase {

    private final UserRepository userRepository;

    public GetProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileResponse execute() {
        // Buscamos el usuario admin por defecto o el primer usuario disponible
        User user = userRepository.findByEmail("admin@portfolio.com")
                .orElseGet(() -> userRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("Profile not found")));

        return ProfileResponse.builder()
                .fullName(user.getFullName())
                .title(user.getTitle())
                .description(user.getDescription())
                .profileImageUrl(user.getProfileImageUrl())
                .email(user.getEmail())
                .build();
    }
}
