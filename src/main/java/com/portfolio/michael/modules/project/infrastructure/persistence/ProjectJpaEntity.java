package com.portfolio.michael.modules.project.infrastructure.persistence;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.auth.infrastructure.persistence.UserJpaEntity;
import com.portfolio.michael.modules.catalog.infrastructure.persistence.ProjectStatusJpaEntity;
import com.portfolio.michael.modules.catalog.infrastructure.persistence.SkillJpaEntity;
import com.portfolio.michael.modules.project.domain.Project;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "projects")
public class ProjectJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private ProjectStatusJpaEntity status;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "project_url", length = 500)
    private String projectUrl;

    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_skills", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @Builder.Default
    private Set<SkillJpaEntity> skills = new HashSet<>();

    public Project toDomain() {
        return Project.builder()
                .id(this.id)
                .user(this.user != null ? this.user.toDomain() : null)
                .status(this.status != null ? this.status.toDomain() : null)
                .name(this.name)
                .description(this.description)
                .projectUrl(this.projectUrl)
                .githubUrl(this.githubUrl)
                .imageUrl(this.imageUrl)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .skills(this.skills.stream().map(SkillJpaEntity::toDomain).collect(Collectors.toSet()))
                .build();
    }

    public static ProjectJpaEntity fromDomain(Project project) {
        return ProjectJpaEntity.builder()
                .id(project.getId())
                .user(project.getUser() != null ? UserJpaEntity.fromDomain(project.getUser()) : null)
                .status(project.getStatus() != null ? ProjectStatusJpaEntity.fromDomain(project.getStatus()) : null)
                .name(project.getName())
                .description(project.getDescription())
                .projectUrl(project.getProjectUrl())
                .githubUrl(project.getGithubUrl())
                .imageUrl(project.getImageUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .skills(project.getSkills().stream().map(SkillJpaEntity::fromDomain).collect(Collectors.toSet()))
                .build();
    }
}
