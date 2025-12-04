package com.portfolio.michael.modules.auth.infrastructure.persistence;

import com.portfolio.michael.modules.auth.domain.Role;

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
@Table(name = "roles")
public class RoleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Role toDomain() {
        return Role.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static RoleJpaEntity fromDomain(Role role) {
        return RoleJpaEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
