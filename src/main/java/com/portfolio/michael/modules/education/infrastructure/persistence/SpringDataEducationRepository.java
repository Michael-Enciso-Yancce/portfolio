package com.portfolio.michael.modules.education.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataEducationRepository extends JpaRepository<EducationJpaEntity, Long> {
    List<EducationJpaEntity> findByUserId(Long userId);
}
