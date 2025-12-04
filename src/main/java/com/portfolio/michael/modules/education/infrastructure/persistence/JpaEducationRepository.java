package com.portfolio.michael.modules.education.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.education.domain.Education;
import com.portfolio.michael.modules.education.domain.EducationRepository;

@Repository
public class JpaEducationRepository implements EducationRepository {

    private final SpringDataEducationRepository jpa;

    public JpaEducationRepository(SpringDataEducationRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Education save(Education domain) {
        EducationJpaEntity entity = EducationJpaEntity.fromDomain(domain);
        return jpa.save(entity).toDomain();
    }

    @Override
    public Optional<Education> findById(Long id) {
        return jpa.findById(id).map(EducationJpaEntity::toDomain);
    }

    @Override
    public List<Education> findAll() {
        return jpa.findAll().stream()
                .map(EducationJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    public List<Education> findByUserId(Long userId) {
        return jpa.findByUserId(userId).stream()
                .map(EducationJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
}
