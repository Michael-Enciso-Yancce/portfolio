package com.portfolio.michael.modules.projectstatus.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.projectstatus.domain.ProjectStatus;
import com.portfolio.michael.modules.projectstatus.domain.ProjectStatusRepository;

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

    @Override
    public void deleteAll() {
        jpa.deleteAll();
    }

    @Override
    public void saveAll(Iterable<ProjectStatus> statuses) {
        java.util.List<ProjectStatusJpaEntity> entities = new java.util.ArrayList<>();
        statuses.forEach(s -> entities.add(ProjectStatusJpaEntity.fromDomain(s)));
        jpa.saveAll(entities);
    }
}
