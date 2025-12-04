package com.portfolio.michael.modules.experience.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataExperienceRepository extends JpaRepository<ExperienceJpaEntity, Long> {
    List<ExperienceJpaEntity> findByUserId(Long userId);
}
