package com.portfolio.michael.modules.skill.application.usecase;

import com.portfolio.michael.modules.skill.domain.UserSkillRepository;

public class DeleteUserSkillUseCase {

    private final UserSkillRepository userSkillRepository;

    public DeleteUserSkillUseCase(UserSkillRepository userSkillRepository) {
        this.userSkillRepository = userSkillRepository;
    }

    public void execute(Long id) {
        userSkillRepository.deleteById(id);
    }
}
