package com.portfolio.michael.modules.catalog.application.usecase;

import com.portfolio.michael.modules.catalog.application.dto.CatalogRequest;
import com.portfolio.michael.modules.catalog.application.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.domain.ProficiencyLevel;
import com.portfolio.michael.modules.catalog.domain.ProficiencyLevelRepository;

public class CreateProficiencyLevelUseCase {

    private final ProficiencyLevelRepository proficiencyLevelRepository;

    public CreateProficiencyLevelUseCase(ProficiencyLevelRepository proficiencyLevelRepository) {
        this.proficiencyLevelRepository = proficiencyLevelRepository;
    }

    public CatalogResponse execute(CatalogRequest request) {
        ProficiencyLevel level = ProficiencyLevel.builder().name(request.getName()).build();
        level = proficiencyLevelRepository.save(level);
        return new CatalogResponse(level.getId(), level.getName());
    }
}
