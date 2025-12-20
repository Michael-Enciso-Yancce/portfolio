package com.portfolio.michael.modules.projectstatus.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProjectStatusRepository extends JpaRepository<ProjectStatusJpaEntity, Long> {
}
