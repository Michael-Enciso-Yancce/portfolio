package com.portfolio.michael.modules.projectstatus.application.usecase;

import com.portfolio.michael.modules.projectstatus.application.dto.ProjectStatusRequest;
import com.portfolio.michael.modules.projectstatus.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.projectstatus.domain.ProjectStatus;
import com.portfolio.michael.modules.projectstatus.domain.ProjectStatusRepository;

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
