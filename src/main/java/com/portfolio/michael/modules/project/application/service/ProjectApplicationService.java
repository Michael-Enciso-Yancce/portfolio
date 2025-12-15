package com.portfolio.michael.modules.project.application.service;

import java.util.List;

import com.portfolio.michael.modules.project.application.dto.CreateProjectRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.application.dto.ProjectStatusRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.project.application.dto.UpdateProjectRequest;
import com.portfolio.michael.modules.project.application.usecase.CreateProjectStatusUseCase;
import com.portfolio.michael.modules.project.application.usecase.CreateProjectUseCase;
import com.portfolio.michael.modules.project.application.usecase.DeleteProjectStatusUseCase;
import com.portfolio.michael.modules.project.application.usecase.DeleteProjectUseCase;
import com.portfolio.michael.modules.project.application.usecase.GetProjectStatusesUseCase;
import com.portfolio.michael.modules.project.application.usecase.GetProjectsUseCase;
import com.portfolio.michael.modules.project.application.usecase.UpdateProjectStatusUseCase;
import com.portfolio.michael.modules.project.application.usecase.UpdateProjectUseCase;

public class ProjectApplicationService {

    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjectsUseCase getProjectsUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;

    // Status Use Cases
    private final GetProjectStatusesUseCase getProjectStatusesUseCase;
    private final CreateProjectStatusUseCase createProjectStatusUseCase;
    private final UpdateProjectStatusUseCase updateProjectStatusUseCase;
    private final DeleteProjectStatusUseCase deleteProjectStatusUseCase;

    public ProjectApplicationService(
            CreateProjectUseCase createProjectUseCase,
            GetProjectsUseCase getProjectsUseCase,
            DeleteProjectUseCase deleteProjectUseCase,
            UpdateProjectUseCase updateProjectUseCase,
            GetProjectStatusesUseCase getProjectStatusesUseCase,
            CreateProjectStatusUseCase createProjectStatusUseCase,
            UpdateProjectStatusUseCase updateProjectStatusUseCase,
            DeleteProjectStatusUseCase deleteProjectStatusUseCase) {
        this.createProjectUseCase = createProjectUseCase;
        this.getProjectsUseCase = getProjectsUseCase;
        this.deleteProjectUseCase = deleteProjectUseCase;
        this.updateProjectUseCase = updateProjectUseCase;
        this.getProjectStatusesUseCase = getProjectStatusesUseCase;
        this.createProjectStatusUseCase = createProjectStatusUseCase;
        this.updateProjectStatusUseCase = updateProjectStatusUseCase;
        this.deleteProjectStatusUseCase = deleteProjectStatusUseCase;
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

    // --- Project Status Operations ---

    public List<ProjectStatusResponse> getAllProjectStatuses() {
        return getProjectStatusesUseCase.execute();
    }

    public ProjectStatusResponse createProjectStatus(ProjectStatusRequest request) {
        return createProjectStatusUseCase.execute(request);
    }

    public ProjectStatusResponse updateProjectStatus(Long id, ProjectStatusRequest request) {
        return updateProjectStatusUseCase.execute(id, request);
    }

    public void deleteProjectStatus(Long id) {
        deleteProjectStatusUseCase.execute(id);
    }
}
