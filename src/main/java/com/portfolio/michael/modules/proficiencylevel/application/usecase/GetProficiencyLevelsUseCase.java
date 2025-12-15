package com.portfolio.michael.modules.proficiencylevel.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.proficiencylevel.application.dto.ProficiencyLevelResponse;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevelRepository;

public class GetProficiencyLevelsUseCase {

    private final ProficiencyLevelRepository proficiencyLevelRepository;

    public GetProficiencyLevelsUseCase(ProficiencyLevelRepository proficiencyLevelRepository) {
        this.proficiencyLevelRepository = proficiencyLevelRepository;
    }

    public List<ProficiencyLevelResponse> execute() {
        return proficiencyLevelRepository.findAll().stream()
                .map(p -> ProficiencyLevelResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
