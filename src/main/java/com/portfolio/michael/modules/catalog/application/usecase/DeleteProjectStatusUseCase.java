package com.portfolio.michael.modules.catalog.application.usecase;

import com.portfolio.michael.modules.catalog.domain.ProjectStatusRepository;

public class DeleteProjectStatusUseCase {

    private final ProjectStatusRepository projectStatusRepository;

    public DeleteProjectStatusUseCase(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    public void execute(Long id) {
        projectStatusRepository.deleteById(id);
    }
}
