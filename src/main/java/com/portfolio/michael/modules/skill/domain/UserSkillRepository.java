package com.portfolio.michael.modules.skill.domain;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository {
    UserSkill save(UserSkill userSkill);

    List<UserSkill> findByUserId(Long userId);

    Optional<UserSkill> findById(Long id);

    void deleteById(Long id);

    boolean existsByUserIdAndSkillId(Long userId, Long skillId);

    Optional<UserSkill> findByUserIdAndSkillId(Long userId, Long skillId);
}
