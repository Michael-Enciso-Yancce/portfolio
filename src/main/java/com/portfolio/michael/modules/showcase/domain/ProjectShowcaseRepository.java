package com.portfolio.michael.modules.showcase.domain;

import java.util.Optional;

import com.portfolio.michael.modules.project.domain.Project;

public interface ProjectShowcaseRepository {
    ProjectShowcase save(ProjectShowcase showcase);

    Optional<ProjectShowcase> findByProjectAndIsCurrentTrue(Project project);

    Optional<Integer> findMaxVersionByProjectId(Long projectId);

    Optional<ProjectShowcase> findById(Long id);
}
