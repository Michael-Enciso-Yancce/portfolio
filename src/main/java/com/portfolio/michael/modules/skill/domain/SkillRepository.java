package com.portfolio.michael.modules.skill.domain;

import java.util.List;
import java.util.Optional;

public interface SkillRepository {
    Optional<Skill> findById(Long id);

    List<Skill> findAllById(Iterable<Long> ids);

    List<Skill> findAll();

    List<Skill> findByCategory(String category);

    Skill save(Skill skill);

    void deleteById(Long id);

    long count();

    void deleteAll();

    void saveAll(Iterable<Skill> skills);
}
