package com.portfolio.michael.modules.catalog.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.catalog.application.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.domain.ProficiencyLevelRepository;

public class GetProficiencyLevelsUseCase {

    private final ProficiencyLevelRepository proficiencyLevelRepository;

    public GetProficiencyLevelsUseCase(ProficiencyLevelRepository proficiencyLevelRepository) {
        this.proficiencyLevelRepository = proficiencyLevelRepository;
    }

    public List<CatalogResponse> execute() {
        return proficiencyLevelRepository.findAll().stream()
                .map(p -> new CatalogResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }
}
