package com.portfolio.michael.modules.project.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProjectRepository extends JpaRepository<ProjectJpaEntity, Long> {
    List<ProjectJpaEntity> findByUserId(Long userId);
}
