package com.portfolio.michael.modules.proficiencylevel.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProficiencyLevelRepository extends JpaRepository<ProficiencyLevelJpaEntity, Long> {
}
