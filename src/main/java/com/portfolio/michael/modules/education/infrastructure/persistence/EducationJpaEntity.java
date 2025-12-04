package com.portfolio.michael.modules.education.infrastructure.persistence;

import java.time.LocalDate;

import com.portfolio.michael.modules.auth.infrastructure.persistence.UserJpaEntity;
import com.portfolio.michael.modules.education.domain.Education;

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
@Table(name = "educations")
public class EducationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private String degree;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public static EducationJpaEntity fromDomain(Education domain) {
        return EducationJpaEntity.builder()
                .id(domain.getId())
                .user(UserJpaEntity.fromDomain(domain.getUser()))
                .institution(domain.getInstitution())
                .degree(domain.getDegree())
                .logoUrl(domain.getLogoUrl())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .build();
    }

    public Education toDomain() {
        return Education.builder()
                .id(this.id)
                .user(this.user.toDomain())
                .institution(this.institution)
                .degree(this.degree)
                .logoUrl(this.logoUrl)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
