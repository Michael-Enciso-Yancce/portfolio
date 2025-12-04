package com.portfolio.michael.modules.catalog.application.usecase;

import com.portfolio.michael.modules.catalog.application.dto.CatalogRequest;
import com.portfolio.michael.modules.catalog.application.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.domain.Skill;
import com.portfolio.michael.modules.catalog.domain.SkillRepository;

public class CreateSkillUseCase {

    private final SkillRepository skillRepository;

    public CreateSkillUseCase(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public CatalogResponse execute(CatalogRequest request) {
        Skill skill = Skill.builder().name(request.getName()).build();
        skill = skillRepository.save(skill);
        return new CatalogResponse(skill.getId(), skill.getName());
    }
}
