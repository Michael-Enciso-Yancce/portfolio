package com.portfolio.michael.modules.skill.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.skill.application.dto.UserSkillResponse;
import com.portfolio.michael.modules.skill.domain.UserSkillRepository;

public class GetUserSkillsUseCase {

    private final UserSkillRepository userSkillRepository;

    public GetUserSkillsUseCase(UserSkillRepository userSkillRepository) {
        this.userSkillRepository = userSkillRepository;
    }

    public List<UserSkillResponse> execute(Long userId) {
        return userSkillRepository.findByUserId(userId).stream()
                .map(userSkill -> UserSkillResponse.builder()
                        .proficiencyLevelName(userSkill.getProficiencyLevel().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
