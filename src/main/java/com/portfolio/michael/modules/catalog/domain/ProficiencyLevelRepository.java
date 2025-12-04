package com.portfolio.michael.modules.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface ProficiencyLevelRepository {
    Optional<ProficiencyLevel> findById(Long id);

    List<ProficiencyLevel> findAll();

    ProficiencyLevel save(ProficiencyLevel proficiencyLevel);

    void deleteById(Long id);
}
