package com.portfolio.michael.modules.catalog.application.usecase;

import com.portfolio.michael.modules.catalog.domain.SkillRepository;

public class DeleteSkillUseCase {

    private final SkillRepository skillRepository;

    public DeleteSkillUseCase(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void execute(Long id) {
        skillRepository.deleteById(id);
    }
}
