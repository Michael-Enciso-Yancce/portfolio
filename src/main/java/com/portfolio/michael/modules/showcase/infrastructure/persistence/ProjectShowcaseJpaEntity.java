package com.portfolio.michael.modules.showcase.infrastructure.persistence;

import java.time.LocalDateTime;

import com.portfolio.michael.modules.project.infrastructure.persistence.ProjectJpaEntity;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcase;
import com.portfolio.michael.modules.showcase.domain.model.ProjectShowcaseContent;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_showcase")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectShowcaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectJpaEntity project;

    @Column(nullable = false)
    private Integer version;

    @Column(columnDefinition = "json", nullable = false)
    @Convert(converter = ProjectShowcaseContentConverter.class)
    private ProjectShowcaseContent content;

    @Column(name = "is_current")
    private boolean isCurrent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public ProjectShowcase toDomain() {
        return ProjectShowcase.builder()
                .id(this.id)
                .project(this.project != null ? this.project.toDomain() : null)
                .version(this.version)
                .content(this.content)
                .isCurrent(this.isCurrent)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static ProjectShowcaseJpaEntity fromDomain(ProjectShowcase domain) {
        return ProjectShowcaseJpaEntity.builder()
                .id(domain.getId())
                .project(domain.getProject() != null ? ProjectJpaEntity.fromDomain(domain.getProject()) : null)
                .version(domain.getVersion())
                .content(domain.getContent())
                .isCurrent(domain.isCurrent())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
