package com.portfolio.michael.modules.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository {
    Optional<UserSkill> findById(Long id);

    List<UserSkill> findByUserId(Long userId);

    UserSkill save(UserSkill userSkill);

    void deleteById(Long id);
}
