package com.portfolio.michael.modules.catalog.domain;

import com.portfolio.michael.modules.auth.domain.User;

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
    private User user;
    private Skill skill;
    private ProficiencyLevel proficiencyLevel;
}
