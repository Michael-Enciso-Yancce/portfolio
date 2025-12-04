package com.portfolio.michael.modules.education.application.mapper;

import org.springframework.stereotype.Component;

import com.portfolio.michael.modules.education.application.dto.EducationRequest;
import com.portfolio.michael.modules.education.application.dto.EducationResponse;
import com.portfolio.michael.modules.education.domain.Education;

@Component
public class EducationMapper {

    public Education toDomain(EducationRequest request) {
        return Education.builder()
                .institution(request.getInstitution())
                .degree(request.getDegree())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    public EducationResponse toResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .institution(education.getInstitution())
                .degree(education.getDegree())
                .logoUrl(education.getLogoUrl())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .build();
    }

    public void updateDomainFromRequest(EducationRequest request, Education education) {
        education.update(
                request.getInstitution(),
                request.getDegree(),
                request.getStartDate(),
                request.getEndDate());
    }
}
