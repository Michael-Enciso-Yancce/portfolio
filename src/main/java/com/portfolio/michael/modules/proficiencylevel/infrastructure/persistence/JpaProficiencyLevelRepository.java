package com.portfolio.michael.modules.proficiencylevel.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevel;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevelRepository;

@Repository
public class JpaProficiencyLevelRepository implements ProficiencyLevelRepository {

    private final SpringDataProficiencyLevelRepository jpa;

    public JpaProficiencyLevelRepository(SpringDataProficiencyLevelRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<ProficiencyLevel> findById(Long id) {
        return jpa.findById(id).map(ProficiencyLevelJpaEntity::toDomain);
    }

    @Override
    public List<ProficiencyLevel> findAll() {
        return jpa.findAll().stream()
                .map(ProficiencyLevelJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ProficiencyLevel save(ProficiencyLevel proficiencyLevel) {
        ProficiencyLevelJpaEntity entity = ProficiencyLevelJpaEntity.fromDomain(proficiencyLevel);
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
    public void saveAll(Iterable<ProficiencyLevel> proficiencyLevels) {
        java.util.List<ProficiencyLevelJpaEntity> entities = new java.util.ArrayList<>();
        proficiencyLevels.forEach(p -> entities.add(ProficiencyLevelJpaEntity.fromDomain(p)));
        jpa.saveAll(entities);
    }
}
