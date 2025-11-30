package com.portfolio.michael.modules.project.service;

import java.util.List;

import com.portfolio.michael.modules.project.dto.ProjectRequest;
import com.portfolio.michael.modules.project.dto.ProjectResponse;

public interface ProjectService {
    List<ProjectResponse> getAllProjects();

    ProjectResponse getProjectById(Long id);

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse updateProject(Long id, ProjectRequest request);

    void deleteProject(Long id);
}
