package com.portfolio.michael.modules.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.catalog.entity.ProjectStatus;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    Optional<ProjectStatus> findByName(String name);
}
