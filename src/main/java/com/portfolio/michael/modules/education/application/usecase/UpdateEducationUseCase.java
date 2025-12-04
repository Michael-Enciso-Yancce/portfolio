package com.portfolio.michael.modules.education.application.usecase;

import com.portfolio.michael.modules.education.application.dto.EducationRequest;
import com.portfolio.michael.modules.education.application.dto.EducationResponse;
import com.portfolio.michael.modules.education.application.mapper.EducationMapper;
import com.portfolio.michael.modules.education.domain.Education;
import com.portfolio.michael.modules.education.domain.EducationRepository;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

public class UpdateEducationUseCase {

    private final EducationRepository educationRepository;
    private final FileStoragePort fileStoragePort;
    private final EducationMapper educationMapper;

    public UpdateEducationUseCase(EducationRepository educationRepository, FileStoragePort fileStoragePort,
            EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.fileStoragePort = fileStoragePort;
        this.educationMapper = educationMapper;
    }

    public EducationResponse execute(Long id, EducationRequest request) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));

        educationMapper.updateDomainFromRequest(request, education);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            // Delete old file
            if (education.getLogoUrl() != null) {
                fileStoragePort.delete(education.getLogoUrl());
            }

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
}
