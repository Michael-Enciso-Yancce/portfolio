package com.portfolio.michael.service.admin.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.dto.admin.request.EducationRequest;
import com.portfolio.michael.dto.admin.response.EducationResponse;
import com.portfolio.michael.entity.Education;
import com.portfolio.michael.entity.File;
import com.portfolio.michael.mapper.EducationMapper;
import com.portfolio.michael.repository.EducationRepository;
import com.portfolio.michael.repository.FileRepository;
import com.portfolio.michael.service.admin.EducationService;
import com.portfolio.michael.service.admin.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final EducationMapper educationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponse> getAllEducations() {
        return educationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EducationResponse getEducationById(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
        return mapToResponse(education);
    }

    @Override
    @Transactional
    public EducationResponse createEducation(EducationRequest request) {
        Education education = educationMapper.toEntity(request);
        education = educationRepository.save(education);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            fileService.saveFile(request.getFile(), "educations", education.getId());
        }

        return mapToResponse(education);
    }

    @Override
    @Transactional
    public EducationResponse updateEducation(Long id, EducationRequest request) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));

        educationMapper.updateEntityFromRequest(request, education);
        educationRepository.save(education);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            // Delete old file if exists
            // Delete old file(s) if exists to ensure only one image per record
            fileRepository.deleteByRelatedTableAndRelatedId("educations", id);

            fileService.saveFile(request.getFile(), "educations", id);
        }

        return mapToResponse(education);
    }

    @Override
    @Transactional
    public void deleteEducation(Long id) {
        if (!educationRepository.existsById(id)) {
            throw new RuntimeException("Education not found with id: " + id);
        }

        // Delete associated file
        Optional<File> file = fileRepository.findByRelatedTableAndRelatedId("educations", id);
        file.ifPresent(fileRepository::delete);

        educationRepository.deleteById(id);
    }

    private EducationResponse mapToResponse(Education education) {
        EducationResponse response = educationMapper.toResponse(education);
        Optional<File> file = fileRepository.findByRelatedTableAndRelatedId("educations", education.getId());

        file.ifPresent(f -> {
            String fileUrl = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/admin/files/")
                    .path(f.getFilePath()) // Assuming filePath stores just the filename
                    .toUriString();
            response.setLogoUrl(fileUrl);
        });

        return response;
    }
}
