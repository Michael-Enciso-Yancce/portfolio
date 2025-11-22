package com.portfolio.michael.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.portfolio.michael.dto.admin.request.EducationRequest;
import com.portfolio.michael.dto.admin.response.EducationResponse;
import com.portfolio.michael.entity.Education;

@Mapper(componentModel = "spring")
public interface EducationMapper {
    Education toEntity(EducationRequest request);

    EducationResponse toResponse(Education education);

    void updateEntityFromRequest(EducationRequest request, @MappingTarget Education education);
}
