package com.portfolio.michael.modules.education.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.education.application.dto.EducationResponse;
import com.portfolio.michael.modules.education.application.mapper.EducationMapper;
import com.portfolio.michael.modules.education.domain.EducationRepository;

public class GetEducationsUseCase {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    public GetEducationsUseCase(EducationRepository educationRepository, EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
    }

    public List<EducationResponse> execute() {
        return educationRepository.findAll().stream()
                .map(educationMapper::toResponse)
                .collect(Collectors.toList());
    }
}
