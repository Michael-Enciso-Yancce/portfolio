package com.portfolio.michael.modules.proficiencylevel.application.usecase;

import com.portfolio.michael.modules.proficiencylevel.application.dto.ProficiencyLevelRequest;
import com.portfolio.michael.modules.proficiencylevel.application.dto.ProficiencyLevelResponse;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevel;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevelRepository;

public class CreateProficiencyLevelUseCase {

    private final ProficiencyLevelRepository proficiencyLevelRepository;

    public CreateProficiencyLevelUseCase(ProficiencyLevelRepository proficiencyLevelRepository) {
        this.proficiencyLevelRepository = proficiencyLevelRepository;
    }

    public ProficiencyLevelResponse execute(ProficiencyLevelRequest request) {
        ProficiencyLevel level = ProficiencyLevel.builder().name(request.getName()).build();
        level = proficiencyLevelRepository.save(level);
        return ProficiencyLevelResponse.builder()
                .id(level.getId())
                .name(level.getName())
                .build();
    }
}
