package com.portfolio.michael.modules.experience.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.auth.entity.User;
import com.portfolio.michael.modules.auth.repository.UserRepository;
import com.portfolio.michael.modules.experience.dto.ExperienceRequest;
import com.portfolio.michael.modules.experience.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.entity.Experience;
import com.portfolio.michael.modules.experience.mapper.ExperienceMapper;
import com.portfolio.michael.modules.experience.repository.ExperienceRepository;
import com.portfolio.michael.modules.experience.service.ExperienceService;
import com.portfolio.michael.modules.file.service.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final ExperienceMapper experienceMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getAllExperiences() {
        return experienceRepository.findAll().stream()
                .map(experienceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceById(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));
        return experienceMapper.toResponse(experience);
    }

    @Override
    @Transactional
    public ExperienceResponse createExperience(ExperienceRequest request) {
        Experience experience = experienceMapper.toEntity(request);

        // Assign User
        experience.setUser(getCurrentUser());

        // Handle Logo
        if (request.getLogo() != null && !request.getLogo().isEmpty()) {
            String logoUrl = fileStorageService.storeFile(request.getLogo());
            experience.setLogoUrl(logoUrl);
        }

        experience = experienceRepository.save(experience);
        return experienceMapper.toResponse(experience);
    }

    @Override
    @Transactional
    public ExperienceResponse updateExperience(Long id, ExperienceRequest request) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));

        experienceMapper.updateEntityFromRequest(request, experience);

        // Update Logo
        if (request.getLogo() != null && !request.getLogo().isEmpty()) {
            if (experience.getLogoUrl() != null) {
                fileStorageService.deleteFile(experience.getLogoUrl());
            }
            String logoUrl = fileStorageService.storeFile(request.getLogo());
            experience.setLogoUrl(logoUrl);
        }

        experience = experienceRepository.save(experience);
        return experienceMapper.toResponse(experience);
    }

    @Override
    @Transactional
    public void deleteExperience(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));

        if (experience.getLogoUrl() != null) {
            fileStorageService.deleteFile(experience.getLogoUrl());
        }

        experienceRepository.deleteById(id);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
