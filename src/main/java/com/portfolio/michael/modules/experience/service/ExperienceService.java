package com.portfolio.michael.modules.experience.service;

import java.util.List;

import com.portfolio.michael.modules.experience.dto.ExperienceRequest;
import com.portfolio.michael.modules.experience.dto.ExperienceResponse;

public interface ExperienceService {
    List<ExperienceResponse> getAllExperiences();

    ExperienceResponse getExperienceById(Long id);

    ExperienceResponse createExperience(ExperienceRequest request);

    ExperienceResponse updateExperience(Long id, ExperienceRequest request);

    void deleteExperience(Long id);
}
