package com.portfolio.michael.modules.catalog.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataUserSkillRepository extends JpaRepository<UserSkillJpaEntity, Long> {
    List<UserSkillJpaEntity> findByUserId(Long userId);
}
