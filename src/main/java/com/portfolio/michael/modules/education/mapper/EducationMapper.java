package com.portfolio.michael.modules.education.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.portfolio.michael.modules.education.dto.EducationRequest;
import com.portfolio.michael.modules.education.dto.EducationResponse;
import com.portfolio.michael.modules.education.entity.Education;

@Mapper(componentModel = "spring")
public interface EducationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "logoUrl", ignore = true)
    Education toEntity(EducationRequest request);

    EducationResponse toResponse(Education education);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "logoUrl", ignore = true)
    void updateEntityFromRequest(EducationRequest request, @MappingTarget Education education);
}
