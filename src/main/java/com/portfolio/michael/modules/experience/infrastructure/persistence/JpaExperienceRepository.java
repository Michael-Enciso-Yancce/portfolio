package com.portfolio.michael.modules.experience.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.experience.domain.Experience;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;

@Repository
public class JpaExperienceRepository implements ExperienceRepository {

    private final SpringDataExperienceRepository jpa;

    public JpaExperienceRepository(SpringDataExperienceRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Experience save(Experience domain) {
        ExperienceJpaEntity entity = ExperienceJpaEntity.fromDomain(domain);
        return jpa.save(entity).toDomain();
    }

    @Override
    public Optional<Experience> findById(Long id) {
        return jpa.findById(id).map(ExperienceJpaEntity::toDomain);
    }

    @Override
    public List<Experience> findAll() {
        return jpa.findAll().stream()
                .map(ExperienceJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    public List<Experience> findByUserId(Long userId) {
        return jpa.findByUserId(userId).stream()
                .map(ExperienceJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
}
