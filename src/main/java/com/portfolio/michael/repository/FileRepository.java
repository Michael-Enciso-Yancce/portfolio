package com.portfolio.michael.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.michael.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByRelatedTableAndRelatedId(String relatedTable, Long relatedId);

    void deleteByRelatedTableAndRelatedId(String relatedTable, Long relatedId);
}
