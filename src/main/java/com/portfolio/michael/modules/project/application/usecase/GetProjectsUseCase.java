package com.portfolio.michael.modules.project.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.skill.application.dto.SkillResponse;
import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetProjectsUseCase {

        private final ProjectRepository projectRepository;

        public List<ProjectResponse> execute() {
                return projectRepository.findAll().stream()
                                .map(this::toResponse)
                                .collect(Collectors.toList());
        }

        private ProjectResponse toResponse(Project project) {
                return ProjectResponse.builder()
                                .id(project.getId())
                                .name(project.getName())
                                .description(project.getDescription())
                                .projectUrl(project.getProjectUrl())
                                .githubUrl(project.getGithubUrl())
                                .imageUrl(project.getImageUrl())
                                .startDate(project.getStartDate())
                                .endDate(project.getEndDate())
                                .status(project.getStatus() != null
                                                ? ProjectStatusResponse.builder()
                                                                .id(project.getStatus().getId())
                                                                .name(project.getStatus().getName())
                                                                .build()
                                                : null)
                                .skills(project.getSkills().stream()
                                                .map(skill -> SkillResponse.builder()
                                                                .id(skill.getId())
                                                                .name(skill.getName())
                                                                .imageUrl(skill.getImageUrl())
                                                                .category(skill.getCategory())
                                                                .build())
                                                .collect(Collectors.toSet()))
                                .build();
        }
}
