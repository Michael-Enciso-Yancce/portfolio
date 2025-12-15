package com.portfolio.michael.modules.project.application.usecase;

import com.portfolio.michael.modules.project.application.dto.ProjectStatusRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.project.domain.ProjectStatus;
import com.portfolio.michael.modules.project.domain.ProjectStatusRepository;
import com.portfolio.michael.shared.exception.ResourceNotFoundException;

public class UpdateProjectStatusUseCase {

    private final ProjectStatusRepository projectStatusRepository;

    public UpdateProjectStatusUseCase(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    public ProjectStatusResponse execute(Long id, ProjectStatusRequest request) {
        ProjectStatus projectStatus = projectStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project Status not found"));

        projectStatus.setName(request.getName());
        projectStatus = projectStatusRepository.save(projectStatus);

        return ProjectStatusResponse.builder()
                .id(projectStatus.getId())
                .name(projectStatus.getName())
                .build();
    }
}
