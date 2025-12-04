package com.portfolio.michael.modules.catalog.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSkillRepository extends JpaRepository<SkillJpaEntity, Long> {
}
