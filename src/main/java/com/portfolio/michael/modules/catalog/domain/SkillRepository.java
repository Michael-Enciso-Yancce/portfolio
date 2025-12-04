package com.portfolio.michael.modules.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface SkillRepository {
    Optional<Skill> findById(Long id);

    List<Skill> findAllById(Iterable<Long> ids);

    List<Skill> findAll();

    Skill save(Skill skill);

    void deleteById(Long id);
}
