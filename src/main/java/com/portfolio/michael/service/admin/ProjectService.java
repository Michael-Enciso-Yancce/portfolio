package com.portfolio.michael.service.admin;

import java.util.List;

import com.portfolio.michael.dto.admin.request.ProjectRequest;
import com.portfolio.michael.dto.admin.response.ProjectResponse;

public interface ProjectService {
    List<ProjectResponse> getAllProjects();

    ProjectResponse getProjectById(Long id);

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse updateProject(Long id, ProjectRequest request);

    void deleteProject(Long id);
}
