package com.portfolio.michael.modules.proficiencylevel.application.usecase;

import com.portfolio.michael.modules.proficiencylevel.application.dto.ProficiencyLevelRequest;
import com.portfolio.michael.modules.proficiencylevel.application.dto.ProficiencyLevelResponse;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevel;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevelRepository;

public class UpdateProficiencyLevelUseCase {

    private final ProficiencyLevelRepository proficiencyLevelRepository;

    public UpdateProficiencyLevelUseCase(ProficiencyLevelRepository proficiencyLevelRepository) {
        this.proficiencyLevelRepository = proficiencyLevelRepository;
    }

    public ProficiencyLevelResponse execute(Long id, ProficiencyLevelRequest request) {
        ProficiencyLevel level = proficiencyLevelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proficiency level not found"));

        level.setName(request.getName());
        level = proficiencyLevelRepository.save(level);

        return ProficiencyLevelResponse.builder()
                .id(level.getId())
                .name(level.getName())
                .build();
    }
}
