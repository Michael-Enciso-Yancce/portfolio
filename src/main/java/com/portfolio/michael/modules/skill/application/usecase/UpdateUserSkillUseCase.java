package com.portfolio.michael.modules.skill.application.usecase;

import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevel;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevelRepository;
import com.portfolio.michael.modules.skill.domain.Skill;
import com.portfolio.michael.modules.skill.domain.SkillRepository;
import com.portfolio.michael.modules.skill.application.dto.UserSkillResponse;
import com.portfolio.michael.modules.skill.domain.UserSkill;
import com.portfolio.michael.modules.skill.domain.UserSkillRepository;

public class UpdateUserSkillUseCase {

    private final UserSkillRepository userSkillRepository;
    private final SkillRepository skillRepository;
    private final ProficiencyLevelRepository proficiencyLevelRepository;

    public UpdateUserSkillUseCase(UserSkillRepository userSkillRepository, SkillRepository skillRepository,
            ProficiencyLevelRepository proficiencyLevelRepository) {
        this.userSkillRepository = userSkillRepository;
        this.skillRepository = skillRepository;
        this.proficiencyLevelRepository = proficiencyLevelRepository;
    }

    public UserSkillResponse execute(Long userId, Long skillId, Long proficiencyLevelId) {

        UserSkill userSkill = userSkillRepository.findByUserId(userId).stream()
                .filter(us -> us.getSkill().getId().equals(skillId))
                .findFirst()
                .orElse(null);

        if (proficiencyLevelId == null) {
            if (userSkill != null) {
                userSkillRepository.deleteById(userSkill.getId());
            }
            // Return empty response or null as checking strict return type
            return UserSkillResponse.builder().build();
        }

        ProficiencyLevel proficiencyLevel = proficiencyLevelRepository.findById(proficiencyLevelId)
                .orElseThrow(() -> new RuntimeException("Proficiency Level not found"));

        UserSkill savedUserSkill;

        if (userSkill != null) {
            // Update existing
            userSkill.setProficiencyLevel(proficiencyLevel);
            savedUserSkill = userSkillRepository.save(userSkill);
        } else {
            // Create new if not exists
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new RuntimeException("Skill not found"));

            UserSkill newUserSkill = UserSkill.builder()
                    .userId(userId)
                    .skill(skill)
                    .proficiencyLevel(proficiencyLevel)
                    .build();
            savedUserSkill = userSkillRepository.save(newUserSkill);
        }

        return UserSkillResponse.builder()
                .proficiencyLevelName(savedUserSkill.getProficiencyLevel().getName())
                .build();
    }
}
