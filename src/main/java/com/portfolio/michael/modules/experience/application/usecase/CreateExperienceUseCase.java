package com.portfolio.michael.modules.experience.application.usecase;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.experience.application.dto.ExperienceRequest;
import com.portfolio.michael.modules.experience.application.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.application.mapper.ExperienceMapper;
import com.portfolio.michael.modules.experience.domain.Experience;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

public class CreateExperienceUseCase {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;
    private final FileStoragePort fileStoragePort;
    private final ExperienceMapper experienceMapper;

    public CreateExperienceUseCase(ExperienceRepository experienceRepository, UserRepository userRepository,
            FileStoragePort fileStoragePort, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
        this.fileStoragePort = fileStoragePort;
        this.experienceMapper = experienceMapper;
    }

    public ExperienceResponse execute(ExperienceRequest request) {
        Experience experience = experienceMapper.toDomain(request);

        // Assign current user
        User currentUser = getCurrentUser();
        experience.setUser(currentUser);

        // Handle file upload
        if (request.getLogo() != null && !request.getLogo().isEmpty()) {
            try {
                FileInput fileInput = FileInput.builder()
                        .filename(request.getLogo().getOriginalFilename())
                        .contentType(request.getLogo().getContentType())
                        .content(request.getLogo().getInputStream())
                        .size(request.getLogo().getSize())
                        .build();

                String logoUrl = fileStoragePort.upload(fileInput, "experiences");
                experience.updateLogoUrl(logoUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload logo", e);
            }
        }

        experience = experienceRepository.save(experience);
        return experienceMapper.toResponse(experience);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
