package com.portfolio.michael.modules.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.portfolio.michael.modules.project.dto.ProjectRequest;
import com.portfolio.michael.modules.project.dto.ProjectResponse;
import com.portfolio.michael.modules.project.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "skills", ignore = true) // Handled in service
    @Mapping(target = "imageUrl", ignore = true) // Handled in service
    @Mapping(target = "user", ignore = true) // Handled in service
    @Mapping(target = "status", ignore = true) // Handled in service
    Project toEntity(ProjectRequest request);

    @Mapping(target = "status.id", source = "status.id")
    @Mapping(target = "status.name", source = "status.name")
    ProjectResponse toResponse(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void updateEntityFromRequest(ProjectRequest request, @MappingTarget Project project);
}
