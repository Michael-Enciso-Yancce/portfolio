package com.portfolio.michael.modules.experience.application.usecase;

import com.portfolio.michael.modules.experience.application.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.application.mapper.ExperienceMapper;
import com.portfolio.michael.modules.experience.domain.Experience;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;

public class GetExperienceByIdUseCase {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    public GetExperienceByIdUseCase(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    public ExperienceResponse execute(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));
        return experienceMapper.toResponse(experience);
    }
}
