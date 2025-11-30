package com.portfolio.michael.modules.experience.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.portfolio.michael.modules.experience.dto.ExperienceRequest;
import com.portfolio.michael.modules.experience.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.entity.Experience;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "logoUrl", ignore = true) // Handled in service
    Experience toEntity(ExperienceRequest request);

    ExperienceResponse toResponse(Experience experience);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "logoUrl", ignore = true)
    void updateEntityFromRequest(ExperienceRequest request, @MappingTarget Experience experience);
}
