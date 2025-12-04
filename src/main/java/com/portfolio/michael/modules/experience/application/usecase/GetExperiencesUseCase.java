package com.portfolio.michael.modules.experience.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.experience.application.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.application.mapper.ExperienceMapper;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;

public class GetExperiencesUseCase {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    public GetExperiencesUseCase(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    public List<ExperienceResponse> execute() {
        return experienceRepository.findAll().stream()
                .map(experienceMapper::toResponse)
                .collect(Collectors.toList());
    }
}
