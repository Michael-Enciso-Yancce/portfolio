package com.portfolio.michael.modules.skill.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataUserSkillRepository extends JpaRepository<UserSkillJpaEntity, Long> {
    List<UserSkillJpaEntity> findByUserId(Long userId);

    Optional<UserSkillJpaEntity> findByUserIdAndSkillId(Long userId, Long skillId);

    boolean existsByUserIdAndSkillId(Long userId, Long skillId);
}
