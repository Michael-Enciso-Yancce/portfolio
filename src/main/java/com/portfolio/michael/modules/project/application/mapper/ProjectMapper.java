package com.portfolio.michael.modules.project.application.mapper;

import java.util.stream.Collectors;

import com.portfolio.michael.modules.catalog.application.dto.CatalogResponse;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.domain.Project;

public class ProjectMapper {

        public static ProjectResponse toResponse(Project project) {
                if (project == null) {
                        return null;
                }
                return ProjectResponse.builder()
                                .id(project.getId())
                                .name(project.getName())
                                .description(project.getDescription())
                                .status(project.getStatus() != null ? CatalogResponse.builder()
                                                .id(project.getStatus().getId())
                                                .name(project.getStatus().getName())
                                                .build() : null)
                                .projectUrl(project.getProjectUrl())
                                .githubUrl(project.getGithubUrl())
                                .imageUrl(project.getImageUrl())
                                .startDate(project.getStartDate())
                                .endDate(project.getEndDate())
                                .skills(project.getSkills().stream()
                                                .map(skill -> CatalogResponse.builder()
                                                                .id(skill.getId())
                                                                .name(skill.getName())
                                                                .build())
                                                .collect(Collectors.toSet()))
                                .build();
        }
}
