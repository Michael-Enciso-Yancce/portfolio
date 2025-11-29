package com.portfolio.michael.service.admin;

import java.util.List;

import com.portfolio.michael.dto.admin.request.ExperienceRequest;
import com.portfolio.michael.dto.admin.response.ExperienceResponse;

public interface ExperienceService {
    List<ExperienceResponse> getAllExperiences();

    ExperienceResponse getExperienceById(Long id);

    ExperienceResponse createExperience(ExperienceRequest request);

    ExperienceResponse updateExperience(Long id, ExperienceRequest request);

    void deleteExperience(Long id);
}
