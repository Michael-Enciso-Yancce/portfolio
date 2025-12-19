package com.portfolio.michael.modules.showcase.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.infrastructure.persistence.ProjectJpaEntity;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcase;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcaseRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaProjectShowcaseRepository implements ProjectShowcaseRepository {

    private final SpringDataProjectShowcaseRepository simpleRepository;
    private final jakarta.persistence.EntityManager entityManager;

    @Override
    public ProjectShowcase save(ProjectShowcase showcase) {
        ProjectShowcaseJpaEntity entity = ProjectShowcaseJpaEntity.fromDomain(showcase);

        // Fix for detached Project entity: Get a reference managed by Hibernate
        if (showcase.getProject() != null && showcase.getProject().getId() != null) {
            ProjectJpaEntity projectRef = entityManager.getReference(ProjectJpaEntity.class,
                    showcase.getProject().getId());
            entity.setProject(projectRef);
        }

        return simpleRepository.save(entity).toDomain();
    }

    @Override
    public Optional<ProjectShowcase> findByProjectAndIsCurrentTrue(Project project) {
        ProjectJpaEntity projectEntity = ProjectJpaEntity.fromDomain(project);
        return simpleRepository.findByProjectAndIsCurrentTrue(projectEntity)
                .map(ProjectShowcaseJpaEntity::toDomain);
    }

    @Override
    public Optional<Integer> findMaxVersionByProjectId(Long projectId) {
        return simpleRepository.findMaxVersionByProjectId(projectId);
    }

    @Override
    public Optional<ProjectShowcase> findById(Long id) {
        return simpleRepository.findById(id)
                .map(ProjectShowcaseJpaEntity::toDomain);
    }

    @Override
    public java.util.List<ProjectShowcase> findAll() {
        return simpleRepository.findAll().stream()
                .map(ProjectShowcaseJpaEntity::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void deleteAll() {
        simpleRepository.deleteAll();
    }

    @Override
    public void saveAll(Iterable<ProjectShowcase> showcases) {
        java.util.List<ProjectShowcaseJpaEntity> entities = new java.util.ArrayList<>();
        showcases.forEach(showcase -> {
            ProjectShowcaseJpaEntity entity = ProjectShowcaseJpaEntity.fromDomain(showcase);
            if (showcase.getProject() != null && showcase.getProject().getId() != null) {
                ProjectJpaEntity projectRef = entityManager.getReference(ProjectJpaEntity.class,
                        showcase.getProject().getId());
                entity.setProject(projectRef);
            }
            entities.add(entity);
        });
        simpleRepository.saveAll(entities);
    }
}
