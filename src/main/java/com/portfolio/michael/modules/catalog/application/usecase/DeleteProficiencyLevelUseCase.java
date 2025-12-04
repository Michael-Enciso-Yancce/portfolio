package com.portfolio.michael.modules.catalog.application.usecase;

import com.portfolio.michael.modules.catalog.domain.ProficiencyLevelRepository;

public class DeleteProficiencyLevelUseCase {

    private final ProficiencyLevelRepository proficiencyLevelRepository;

    public DeleteProficiencyLevelUseCase(ProficiencyLevelRepository proficiencyLevelRepository) {
        this.proficiencyLevelRepository = proficiencyLevelRepository;
    }

    public void execute(Long id) {
        proficiencyLevelRepository.deleteById(id);
    }
}
