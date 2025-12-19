package com.portfolio.michael.modules.education.domain;

import java.util.List;
import java.util.Optional;

public interface EducationRepository {
    List<Education> findAll();

    Optional<Education> findById(Long id);

    List<Education> findByUserId(Long userId);

    long countByUserId(Long userId);

    long count();

    Education save(Education education);

    void deleteById(Long id);

    void deleteAll();

    void saveAll(Iterable<Education> educations);
}
