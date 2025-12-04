package com.portfolio.michael.modules.experience.application.usecase;

import java.io.IOException;

import com.portfolio.michael.modules.experience.application.dto.ExperienceRequest;
import com.portfolio.michael.modules.experience.application.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.application.mapper.ExperienceMapper;
import com.portfolio.michael.modules.experience.domain.Experience;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

public class UpdateExperienceUseCase {

    private final ExperienceRepository experienceRepository;
    private final FileStoragePort fileStoragePort;
    private final ExperienceMapper experienceMapper;

    public UpdateExperienceUseCase(ExperienceRepository experienceRepository, FileStoragePort fileStoragePort,
            ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.fileStoragePort = fileStoragePort;
        this.experienceMapper = experienceMapper;
    }

    public ExperienceResponse execute(Long id, ExperienceRequest request) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));

        experienceMapper.updateDomainFromRequest(request, experience);

        if (request.getLogo() != null && !request.getLogo().isEmpty()) {
            // Delete old file
            if (experience.getLogoUrl() != null) {
                fileStoragePort.delete(experience.getLogoUrl());
            }

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
}
