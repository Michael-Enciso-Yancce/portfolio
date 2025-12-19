package com.portfolio.michael.modules.showcase.domain;

import java.util.Optional;
import java.util.List;

import com.portfolio.michael.modules.project.domain.Project;

public interface ProjectShowcaseRepository {
    ProjectShowcase save(ProjectShowcase showcase);

    Optional<ProjectShowcase> findByProjectAndIsCurrentTrue(Project project);

    Optional<Integer> findMaxVersionByProjectId(Long projectId);

    Optional<ProjectShowcase> findById(Long id);

    // Backup support
    List<ProjectShowcase> findAll();

    void deleteAll();

    void saveAll(Iterable<ProjectShowcase> showcases);
}
