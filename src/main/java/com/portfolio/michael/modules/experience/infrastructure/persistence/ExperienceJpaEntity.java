package com.portfolio.michael.modules.experience.infrastructure.persistence;

import java.time.LocalDate;

import com.portfolio.michael.modules.auth.infrastructure.persistence.UserJpaEntity;
import com.portfolio.michael.modules.experience.domain.Experience;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "experiences")
public class ExperienceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public static ExperienceJpaEntity fromDomain(Experience domain) {
        return ExperienceJpaEntity.builder()
                .id(domain.getId())
                .user(UserJpaEntity.fromDomain(domain.getUser()))
                .companyName(domain.getCompanyName())
                .role(domain.getRole())
                .description(domain.getDescription())
                .logoUrl(domain.getLogoUrl())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .build();
    }

    public Experience toDomain() {
        return Experience.builder()
                .id(this.id)
                .user(this.user.toDomain())
                .companyName(this.companyName)
                .role(this.role)
                .description(this.description)
                .logoUrl(this.logoUrl)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
