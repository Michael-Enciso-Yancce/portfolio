package com.portfolio.michael.modules.project.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.project.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.project.domain.ProjectStatusRepository;

public class GetProjectStatusesUseCase {

    private final ProjectStatusRepository projectStatusRepository;

    public GetProjectStatusesUseCase(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    public List<ProjectStatusResponse> execute() {
        return projectStatusRepository.findAll().stream()
                .map(p -> ProjectStatusResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
