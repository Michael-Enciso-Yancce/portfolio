package com.portfolio.michael.modules.skill.application.usecase;

import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevel;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevelRepository;
import com.portfolio.michael.modules.skill.application.dto.UserSkillRequest;
import com.portfolio.michael.modules.skill.application.dto.UserSkillResponse;
import com.portfolio.michael.modules.skill.domain.Skill;
import com.portfolio.michael.modules.skill.domain.SkillRepository;
import com.portfolio.michael.modules.skill.domain.UserSkill;
import com.portfolio.michael.modules.skill.domain.UserSkillRepository;
import com.portfolio.michael.shared.exception.ResourceNotFoundException;

public class CreateUserSkillUseCase {

        private final UserSkillRepository userSkillRepository;
        private final SkillRepository skillRepository;
        private final ProficiencyLevelRepository proficiencyLevelRepository;

        public CreateUserSkillUseCase(UserSkillRepository userSkillRepository, SkillRepository skillRepository,
                        ProficiencyLevelRepository proficiencyLevelRepository) {
                this.userSkillRepository = userSkillRepository;
                this.skillRepository = skillRepository;
                this.proficiencyLevelRepository = proficiencyLevelRepository;
        }

        public UserSkillResponse execute(Long userId, UserSkillRequest request) {
                if (userSkillRepository.existsByUserIdAndSkillId(userId, request.getSkillId())) {
                        throw new IllegalArgumentException("User already has this skill");
                }

                Skill skill = skillRepository.findById(request.getSkillId())
                                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

                ProficiencyLevel proficiencyLevel = proficiencyLevelRepository.findById(request.getProficiencyLevelId())
                                .orElseThrow(() -> new ResourceNotFoundException("Proficiency Level not found"));

                UserSkill userSkill = UserSkill.builder()
                                .userId(userId)
                                .skill(skill)
                                .proficiencyLevel(proficiencyLevel)
                                .build();

                userSkill = userSkillRepository.save(userSkill);

                return UserSkillResponse.builder()
                                .proficiencyLevelName(userSkill.getProficiencyLevel().getName())
                                .build();
        }
}
