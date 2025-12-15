package com.portfolio.michael.modules.showcase.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.project.infrastructure.persistence.ProjectJpaEntity;

@Repository
public interface SpringDataProjectShowcaseRepository extends JpaRepository<ProjectShowcaseJpaEntity, Long> {

    Optional<ProjectShowcaseJpaEntity> findByProjectAndIsCurrentTrue(ProjectJpaEntity project);

    @Query("SELECT MAX(ps.version) FROM ProjectShowcaseJpaEntity ps WHERE ps.project.id = :projectId")
    Optional<Integer> findMaxVersionByProjectId(Long projectId);
}
