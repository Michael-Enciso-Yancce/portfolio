package com.portfolio.michael.modules.education.application.usecase;

import com.portfolio.michael.modules.education.domain.Education;
import com.portfolio.michael.modules.education.domain.EducationRepository;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

public class DeleteEducationUseCase {

    private final EducationRepository educationRepository;
    private final FileStoragePort fileStoragePort;

    public DeleteEducationUseCase(EducationRepository educationRepository, FileStoragePort fileStoragePort) {
        this.educationRepository = educationRepository;
        this.fileStoragePort = fileStoragePort;
    }

    public void execute(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));

        // Delete associated file
        if (education.getLogoUrl() != null) {
            fileStoragePort.delete(education.getLogoUrl());
        }

        educationRepository.deleteById(id);
    }
}
