package com.portfolio.michael.modules.project.domain;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);

    Optional<Project> findById(Long id);

    List<Project> findAll();

    void deleteById(Long id);

    List<Project> findByUserId(Long userId);
}
