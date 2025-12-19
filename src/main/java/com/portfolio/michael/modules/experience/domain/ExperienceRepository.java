package com.portfolio.michael.modules.experience.domain;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository {
    List<Experience> findAll();

    Optional<Experience> findById(Long id);

    long countByUserId(Long userId);

    long count();

    Experience save(Experience experience);

    void deleteById(Long id);

    void deleteAll();

    void saveAll(Iterable<Experience> experiences);
}
