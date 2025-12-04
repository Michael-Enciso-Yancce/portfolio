package com.portfolio.michael.modules.education.application.usecase;

import com.portfolio.michael.modules.education.application.dto.EducationResponse;
import com.portfolio.michael.modules.education.application.mapper.EducationMapper;
import com.portfolio.michael.modules.education.domain.Education;
import com.portfolio.michael.modules.education.domain.EducationRepository;

public class GetEducationByIdUseCase {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    public GetEducationByIdUseCase(EducationRepository educationRepository, EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
    }

    public EducationResponse execute(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
        return educationMapper.toResponse(education);
    }
}
