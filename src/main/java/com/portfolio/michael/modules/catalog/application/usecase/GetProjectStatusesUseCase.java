package com.portfolio.michael.modules.catalog.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.catalog.application.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.domain.ProjectStatusRepository;

public class GetProjectStatusesUseCase {

    private final ProjectStatusRepository projectStatusRepository;

    public GetProjectStatusesUseCase(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    public List<CatalogResponse> execute() {
        return projectStatusRepository.findAll().stream()
                .map(p -> new CatalogResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }
}
