package com.portfolio.michael.modules.skill.infrastructure.persistence;

import com.portfolio.michael.modules.auth.infrastructure.persistence.UserJpaEntity;
import com.portfolio.michael.modules.proficiencylevel.infrastructure.persistence.ProficiencyLevelJpaEntity;
import com.portfolio.michael.modules.skill.domain.UserSkill;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_skills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private SkillJpaEntity skill;

    @ManyToOne
    @JoinColumn(name = "proficiency_level_id", nullable = false)
    private ProficiencyLevelJpaEntity proficiencyLevel;

    public static UserSkillJpaEntity fromDomain(UserSkill domain) {
        return UserSkillJpaEntity.builder()
                .id(domain.getId())
                .user(UserJpaEntity.builder().id(domain.getUserId()).build())
                .skill(SkillJpaEntity.fromDomain(domain.getSkill()))
                .proficiencyLevel(ProficiencyLevelJpaEntity.fromDomain(domain.getProficiencyLevel()))
                .build();
    }

    public UserSkill toDomain() {
        return UserSkill.builder()
                .id(this.id)
                .userId(this.user.getId())
                .skill(this.skill.toDomain())
                .proficiencyLevel(this.proficiencyLevel.toDomain())
                .build();
    }
}
