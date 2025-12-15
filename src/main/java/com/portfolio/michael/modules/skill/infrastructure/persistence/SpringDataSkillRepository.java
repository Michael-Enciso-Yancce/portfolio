package com.portfolio.michael.modules.skill.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSkillRepository extends JpaRepository<SkillJpaEntity, Long> {
    List<SkillJpaEntity> findByCategory(String category);
}
