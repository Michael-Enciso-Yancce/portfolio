package com.portfolio.michael.modules.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.catalog.entity.ProficiencyLevel;

@Repository
public interface ProficiencyLevelRepository extends JpaRepository<ProficiencyLevel, Long> {
    Optional<ProficiencyLevel> findByName(String name);
}
