package com.portfolio.michael.modules.catalog.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.catalog.application.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.domain.SkillRepository;

public class GetSkillsUseCase {

    private final SkillRepository skillRepository;

    public GetSkillsUseCase(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<CatalogResponse> execute() {
        return skillRepository.findAll().stream()
                .map(s -> new CatalogResponse(s.getId(), s.getName()))
                .collect(Collectors.toList());
    }
}
