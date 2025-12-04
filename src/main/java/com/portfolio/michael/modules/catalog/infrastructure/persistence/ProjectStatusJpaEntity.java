package com.portfolio.michael.modules.catalog.infrastructure.persistence;

import com.portfolio.michael.modules.catalog.domain.ProjectStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_statuses")
public class ProjectStatusJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public ProjectStatus toDomain() {
        return ProjectStatus.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static ProjectStatusJpaEntity fromDomain(ProjectStatus status) {
        return ProjectStatusJpaEntity.builder()
                .id(status.getId())
                .name(status.getName())
                .build();
    }
}
