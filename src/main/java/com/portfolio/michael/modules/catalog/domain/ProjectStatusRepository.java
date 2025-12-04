package com.portfolio.michael.modules.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface ProjectStatusRepository {
    Optional<ProjectStatus> findById(Long id);

    List<ProjectStatus> findAll();

    ProjectStatus save(ProjectStatus status);

    void deleteById(Long id);
}
