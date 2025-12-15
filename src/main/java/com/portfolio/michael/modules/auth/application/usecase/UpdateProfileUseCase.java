package com.portfolio.michael.modules.auth.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.auth.application.dto.AuthResponse;
import com.portfolio.michael.modules.auth.application.dto.UpdateProfileRequest;
import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProfileUseCase {

    private final UserRepository userRepository;
    private final FileStoragePort fileStoragePort;

    @Transactional
    public AuthResponse execute(String email, UpdateProfileRequest request, FileInput profileImage) {
        // 1. Find user by email (from authenticated user)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Update only non-null fields
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getTitle() != null) {
            user.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            user.setDescription(request.getDescription());
        }
        if (request.getEmail() != null) {
            // Validate that the new email is not already in use
            if (!request.getEmail().equals(email)) {
                userRepository.findByEmail(request.getEmail())
                        .ifPresent(u -> {
                            throw new RuntimeException("Email already in use");
                        });
            }
            user.setEmail(request.getEmail());
        }

        // 3. Upload profile image if provided
        if (profileImage != null) {
            String imageUrl = fileStoragePort.upload(profileImage, "profiles");
            user.setProfileImageUrl(imageUrl);
        }

        // 4. Save changes
        User updated = userRepository.save(user);

        // 5. Return response
        return AuthResponse.builder()
                .fullName(updated.getFullName())
                .email(updated.getEmail())
                .profileImageUrl(updated.getProfileImageUrl())
                .build();
    }
}
