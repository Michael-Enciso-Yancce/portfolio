package com.portfolio.michael.modules.projectstatus.application.usecase;

import com.portfolio.michael.modules.projectstatus.domain.ProjectStatusRepository;

public class DeleteProjectStatusUseCase {

    private final ProjectStatusRepository projectStatusRepository;

    public DeleteProjectStatusUseCase(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    public void execute(Long id) {
        projectStatusRepository.deleteById(id);
    }
}
