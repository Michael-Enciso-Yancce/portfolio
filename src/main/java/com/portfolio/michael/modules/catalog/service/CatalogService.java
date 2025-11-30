package com.portfolio.michael.modules.catalog.service;

import java.util.List;

import com.portfolio.michael.modules.catalog.dto.CatalogRequest;
import com.portfolio.michael.modules.catalog.dto.CatalogResponse;

public interface CatalogService {
    // Skills
    List<CatalogResponse> getAllSkills();

    CatalogResponse createSkill(CatalogRequest request);

    void deleteSkill(Long id);

    // Proficiency Levels
    List<CatalogResponse> getAllProficiencyLevels();

    CatalogResponse createProficiencyLevel(CatalogRequest request);

    void deleteProficiencyLevel(Long id);

    // Project Statuses
    List<CatalogResponse> getAllProjectStatuses();

    CatalogResponse createProjectStatus(CatalogRequest request);

    void deleteProjectStatus(Long id);
}
