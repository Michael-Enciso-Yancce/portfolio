package com.portfolio.michael.modules.education.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.auth.entity.User;
import com.portfolio.michael.modules.auth.repository.UserRepository;
import com.portfolio.michael.modules.education.dto.EducationRequest;
import com.portfolio.michael.modules.education.dto.EducationResponse;
import com.portfolio.michael.modules.education.entity.Education;
import com.portfolio.michael.modules.education.mapper.EducationMapper;
import com.portfolio.michael.modules.education.repository.EducationRepository;
import com.portfolio.michael.modules.education.service.EducationService;
import com.portfolio.michael.modules.file.service.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final EducationMapper educationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponse> getAllEducations() {
        return educationRepository.findAll().stream()
                .map(educationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EducationResponse getEducationById(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
        return educationMapper.toResponse(education);
    }

    @Override
    @Transactional
    public EducationResponse createEducation(EducationRequest request) {
        Education education = educationMapper.toEntity(request);

        // Assign current user
        User currentUser = getCurrentUser();
        education.setUser(currentUser);

        // Handle file upload
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            String fileUrl = fileStorageService.storeFile(request.getFile());
            education.setLogoUrl(fileUrl);
        }

        education = educationRepository.save(education);
        return educationMapper.toResponse(education);
    }

    @Override
    @Transactional
    public EducationResponse updateEducation(Long id, EducationRequest request) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));

        // Verify ownership (optional but recommended)
        // if (!education.getUser().getId().equals(getCurrentUser().getId())) {
        // throw new RuntimeException("You are not authorized to update this
        // education");
        // }

        educationMapper.updateEntityFromRequest(request, education);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            // Delete old file
            if (education.getLogoUrl() != null) {
                fileStorageService.deleteFile(education.getLogoUrl());
            }

            String fileUrl = fileStorageService.storeFile(request.getFile());
            education.setLogoUrl(fileUrl);
        }

        education = educationRepository.save(education);
        return educationMapper.toResponse(education);
    }

    @Override
    @Transactional
    public void deleteEducation(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));

        // Delete associated file
        if (education.getLogoUrl() != null) {
            fileStorageService.deleteFile(education.getLogoUrl());
        }

        educationRepository.deleteById(id);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
