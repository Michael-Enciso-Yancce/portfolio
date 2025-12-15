package com.portfolio.michael.modules.publicapi.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectRepository;
import com.portfolio.michael.modules.publicapi.application.dto.ProjectPublicResponse;

public class GetPublicProjectsUseCase {

    private final ProjectRepository projectRepository;

    public GetPublicProjectsUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectPublicResponse> execute() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(this::toPublicResponse)
                .collect(Collectors.toList());
    }

    private ProjectPublicResponse toPublicResponse(Project project) {
        return ProjectPublicResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .imageUrl(project.getImageUrl())
                .projectUrl(project.getProjectUrl())
                .githubUrl(project.getGithubUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .status(project.getStatus() != null ? project.getStatus().getName() : null)
                .skills(project.getSkills().stream()
                        .map(skill -> ProjectPublicResponse.SkillInfo.builder()
                                .id(skill.getId())
                                .name(skill.getName())
                                .category(skill.getCategory())
                                .imageUrl(skill.getImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
