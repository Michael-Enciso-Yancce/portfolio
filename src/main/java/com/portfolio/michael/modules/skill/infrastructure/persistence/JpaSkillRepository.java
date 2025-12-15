package com.portfolio.michael.modules.skill.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.skill.domain.Skill;
import com.portfolio.michael.modules.skill.domain.SkillRepository;

@Repository
public class JpaSkillRepository implements SkillRepository {

    private final SpringDataSkillRepository jpa;

    public JpaSkillRepository(SpringDataSkillRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Skill> findById(Long id) {
        return jpa.findById(id).map(SkillJpaEntity::toDomain);
    }

    @Override
    public List<Skill> findAllById(Iterable<Long> ids) {
        return jpa.findAllById(ids).stream()
                .map(SkillJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Skill> findAll() {
        return jpa.findAll().stream()
                .map(SkillJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Skill> findByCategory(String category) {
        return jpa.findByCategory(category).stream()
                .map(SkillJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Skill save(Skill skill) {
        SkillJpaEntity entity = SkillJpaEntity.fromDomain(skill);
        return jpa.save(entity).toDomain();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public long count() {
        return jpa.count();
    }
}
