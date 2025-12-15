package com.portfolio.michael.modules.skill.domain;

import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill {
    private Long id;
    private Long userId;
    private Skill skill;
    private ProficiencyLevel proficiencyLevel;
}
