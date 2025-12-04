package com.portfolio.michael.modules.catalog.application.usecase;

import com.portfolio.michael.modules.catalog.application.dto.CatalogRequest;
import com.portfolio.michael.modules.catalog.application.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.domain.ProjectStatus;
import com.portfolio.michael.modules.catalog.domain.ProjectStatusRepository;

public class CreateProjectStatusUseCase {

    private final ProjectStatusRepository projectStatusRepository;

    public CreateProjectStatusUseCase(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    public CatalogResponse execute(CatalogRequest request) {
        ProjectStatus status = ProjectStatus.builder().name(request.getName()).build();
        status = projectStatusRepository.save(status);
        return new CatalogResponse(status.getId(), status.getName());
    }
}
