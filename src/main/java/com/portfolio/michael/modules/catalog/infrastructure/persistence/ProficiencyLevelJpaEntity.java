package com.portfolio.michael.modules.catalog.infrastructure.persistence;

import com.portfolio.michael.modules.catalog.domain.ProficiencyLevel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proficiency_levels")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProficiencyLevelJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public static ProficiencyLevelJpaEntity fromDomain(ProficiencyLevel domain) {
        return ProficiencyLevelJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    public ProficiencyLevel toDomain() {
        return ProficiencyLevel.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
