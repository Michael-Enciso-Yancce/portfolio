package com.portfolio.michael.modules.experience.application.mapper;

import org.springframework.stereotype.Component;

import com.portfolio.michael.modules.experience.application.dto.ExperienceRequest;
import com.portfolio.michael.modules.experience.application.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.domain.Experience;

@Component
public class ExperienceMapper {

    public Experience toDomain(ExperienceRequest request) {
        return Experience.builder()
                .companyName(request.getCompanyName())
                .role(request.getRole())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    public ExperienceResponse toResponse(Experience experience) {
        return ExperienceResponse.builder()
                .id(experience.getId())
                .companyName(experience.getCompanyName())
                .role(experience.getRole())
                .description(experience.getDescription())
                .logoUrl(experience.getLogoUrl())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .build();
    }

    public void updateDomainFromRequest(ExperienceRequest request, Experience experience) {
        experience.update(
                request.getCompanyName(),
                request.getRole(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate());
    }
}
