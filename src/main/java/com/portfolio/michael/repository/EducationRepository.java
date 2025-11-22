package com.portfolio.michael.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.michael.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
}
