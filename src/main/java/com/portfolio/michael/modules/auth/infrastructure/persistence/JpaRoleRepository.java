package com.portfolio.michael.modules.auth.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.auth.domain.Role;
import com.portfolio.michael.modules.auth.domain.RoleRepository;

@Repository
public class JpaRoleRepository implements RoleRepository {

    private final SpringDataRoleRepository jpa;

    public JpaRoleRepository(SpringDataRoleRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpa.findByName(name).map(RoleJpaEntity::toDomain);
    }

    @Override
    public Role save(Role role) {
        RoleJpaEntity entity = RoleJpaEntity.fromDomain(role);
        return jpa.save(entity).toDomain();
    }
}
