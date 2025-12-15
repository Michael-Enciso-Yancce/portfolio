package com.portfolio.michael.modules.project.application.usecase;

import com.portfolio.michael.modules.project.application.dto.ProjectStatusRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.project.domain.ProjectStatus;
import com.portfolio.michael.modules.project.domain.ProjectStatusRepository;

public class CreateProjectStatusUseCase {

    private final ProjectStatusRepository projectStatusRepository;

    public CreateProjectStatusUseCase(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    public ProjectStatusResponse execute(ProjectStatusRequest request) {
        ProjectStatus status = ProjectStatus.builder().name(request.getName()).build();
        status = projectStatusRepository.save(status);
        return ProjectStatusResponse.builder()
                .id(status.getId())
                .name(status.getName())
                .build();
    }
}
