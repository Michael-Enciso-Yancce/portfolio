package com.portfolio.michael.modules.project.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectRepository;

@Repository
public class JpaProjectRepository implements ProjectRepository {

    private final SpringDataProjectRepository jpa;

    public JpaProjectRepository(SpringDataProjectRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Project save(Project domain) {
        ProjectJpaEntity entity = ProjectJpaEntity.fromDomain(domain);
        return jpa.save(entity).toDomain();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return jpa.findById(id).map(ProjectJpaEntity::toDomain);
    }

    @Override
    public List<Project> findAll() {
        return jpa.findAll().stream()
                .map(ProjectJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        return jpa.countByUserId(userId);
    }

    @Override
    public List<Project> findRecentByUserId(Long userId) {
        return jpa.findTop5ByUserIdOrderByIdDesc(userId).stream()
                .map(ProjectJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jpa.count();
    }

    @Override
    public List<Project> findByUserId(Long userId) {
        return jpa.findByUserId(userId).stream()
                .map(ProjectJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
}
