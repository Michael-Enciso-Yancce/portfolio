package com.portfolio.michael.modules.education.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.education.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
}
