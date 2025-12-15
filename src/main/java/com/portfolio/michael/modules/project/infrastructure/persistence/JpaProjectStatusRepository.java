package com.portfolio.michael.modules.project.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.project.domain.ProjectStatus;
import com.portfolio.michael.modules.project.domain.ProjectStatusRepository;

@Repository
public class JpaProjectStatusRepository implements ProjectStatusRepository {

    private final SpringDataProjectStatusRepository jpa;

    public JpaProjectStatusRepository(SpringDataProjectStatusRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<ProjectStatus> findById(Long id) {
        return jpa.findById(id).map(ProjectStatusJpaEntity::toDomain);
    }

    @Override
    public List<ProjectStatus> findAll() {
        return jpa.findAll().stream()
                .map(ProjectStatusJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectStatus save(ProjectStatus status) {
        ProjectStatusJpaEntity entity = ProjectStatusJpaEntity.fromDomain(status);
        return jpa.save(entity).toDomain();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
