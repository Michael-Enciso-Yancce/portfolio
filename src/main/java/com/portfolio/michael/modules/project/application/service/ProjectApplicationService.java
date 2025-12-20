package com.portfolio.michael.modules.project.application.service;

import java.util.List;

import com.portfolio.michael.modules.project.application.dto.CreateProjectRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.application.dto.UpdateProjectRequest;
import com.portfolio.michael.modules.project.application.usecase.CreateProjectUseCase;
import com.portfolio.michael.modules.project.application.usecase.DeleteProjectUseCase;
import com.portfolio.michael.modules.project.application.usecase.GetProjectsUseCase;
import com.portfolio.michael.modules.project.application.usecase.UpdateProjectUseCase;

public class ProjectApplicationService {

    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjectsUseCase getProjectsUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;

    public ProjectApplicationService(
            CreateProjectUseCase createProjectUseCase,
            GetProjectsUseCase getProjectsUseCase,
            DeleteProjectUseCase deleteProjectUseCase,
            UpdateProjectUseCase updateProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
        this.getProjectsUseCase = getProjectsUseCase;
        this.deleteProjectUseCase = deleteProjectUseCase;
        this.updateProjectUseCase = updateProjectUseCase;
    }

    // --- Project Operations ---

    public ProjectResponse createProject(CreateProjectRequest request) {
        return createProjectUseCase.execute(request);
    }

    public List<ProjectResponse> getAllProjects() {
        return getProjectsUseCase.execute();
    }

    public ProjectResponse updateProject(Long id, UpdateProjectRequest request) {
        return updateProjectUseCase.execute(id, request);
    }

    public void deleteProject(Long id) {
        deleteProjectUseCase.execute(id);
    }

}
