package com.portfolio.michael.modules.skill.infrastructure.persistence;

import com.portfolio.michael.modules.skill.domain.Skill;

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
@Table(name = "skills")
public class SkillJpaEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String name;

        @Column(name = "image_url", length = 500)
        private String imageUrl;

        @Column(length = 50)
        private String category;

        public Skill toDomain() {
                return Skill.builder()
                                .id(this.id)
                                .name(this.name)
                                .imageUrl(this.imageUrl)
                                .category(this.category)
                                .build();
        }

        public static SkillJpaEntity fromDomain(Skill skill) {
                return SkillJpaEntity.builder()
                                .id(skill.getId())
                                .name(skill.getName())
                                .imageUrl(skill.getImageUrl())
                                .category(skill.getCategory())
                                .build();
        }
}
