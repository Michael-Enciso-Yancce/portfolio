package com.portfolio.michael.modules.experience.application.usecase;

import com.portfolio.michael.modules.experience.domain.Experience;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

public class DeleteExperienceUseCase {

    private final ExperienceRepository experienceRepository;
    private final FileStoragePort fileStoragePort;

    public DeleteExperienceUseCase(ExperienceRepository experienceRepository, FileStoragePort fileStoragePort) {
        this.experienceRepository = experienceRepository;
        this.fileStoragePort = fileStoragePort;
    }

    public void execute(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));

        // Delete associated file
        if (experience.getLogoUrl() != null) {
            fileStoragePort.delete(experience.getLogoUrl());
        }

        experienceRepository.deleteById(id);
    }
}
