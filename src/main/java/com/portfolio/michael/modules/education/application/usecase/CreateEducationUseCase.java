package com.portfolio.michael.modules.education.application.usecase;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.education.application.dto.EducationRequest;
import com.portfolio.michael.modules.education.application.dto.EducationResponse;
import com.portfolio.michael.modules.education.application.mapper.EducationMapper;
import com.portfolio.michael.modules.education.domain.Education;
import com.portfolio.michael.modules.education.domain.EducationRepository;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

public class CreateEducationUseCase {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;
    private final FileStoragePort fileStoragePort;
    private final EducationMapper educationMapper;

    public CreateEducationUseCase(EducationRepository educationRepository, UserRepository userRepository,
            FileStoragePort fileStoragePort, EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
        this.fileStoragePort = fileStoragePort;
        this.educationMapper = educationMapper;
    }

    public EducationResponse execute(EducationRequest request) {
        Education education = educationMapper.toDomain(request);

        // Assign current user
        User currentUser = getCurrentUser();
        education.setUser(currentUser);

        // Handle file upload
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            try {
                FileInput fileInput = FileInput.builder()
                        .filename(request.getFile().getOriginalFilename())
                        .contentType(request.getFile().getContentType())
                        .content(request.getFile().getInputStream())
                        .size(request.getFile().getSize())
                        .build();

                String fileUrl = fileStoragePort.upload(fileInput, "educations");
                education.updateLogoUrl(fileUrl);
            } catch (java.io.IOException e) {
                throw new RuntimeException("Failed to upload file", e);
            }
        }

        education = educationRepository.save(education);
        return educationMapper.toResponse(education);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
