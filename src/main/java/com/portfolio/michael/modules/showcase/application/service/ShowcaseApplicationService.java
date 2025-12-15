package com.portfolio.michael.modules.showcase.application.service;

import java.util.Optional;

import com.portfolio.michael.modules.showcase.application.dto.CreateShowcaseRequest;
import com.portfolio.michael.modules.showcase.application.usecase.CreateProjectShowcaseUseCase;
import com.portfolio.michael.modules.showcase.application.usecase.GetProjectShowcaseUseCase;
import com.portfolio.michael.modules.showcase.application.usecase.UpdateProjectShowcaseUseCase;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcase;

public class ShowcaseApplicationService {

    private final CreateProjectShowcaseUseCase createUseCase;
    private final GetProjectShowcaseUseCase getUseCase;
    private final UpdateProjectShowcaseUseCase updateUseCase;

    public ShowcaseApplicationService(
            CreateProjectShowcaseUseCase createUseCase,
            GetProjectShowcaseUseCase getUseCase,
            UpdateProjectShowcaseUseCase updateUseCase) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.updateUseCase = updateUseCase;
    }

    public ProjectShowcase createShowcase(CreateShowcaseRequest request) {
        return createUseCase.execute(request);
    }

    public ProjectShowcase updateShowcase(Long id, CreateShowcaseRequest request) {
        return updateUseCase.execute(id, request);
    }

    public Optional<ProjectShowcase> getShowcaseByProjectId(Long projectId) {
        return getUseCase.execute(projectId);
    }
}
