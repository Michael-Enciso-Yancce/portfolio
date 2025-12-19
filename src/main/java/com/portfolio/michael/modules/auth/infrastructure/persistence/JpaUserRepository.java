package com.portfolio.michael.modules.auth.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;

@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserRepository jpa;

    public JpaUserRepository(SpringDataUserRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpa.findById(id).map(UserJpaEntity::toDomain);
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = UserJpaEntity.fromDomain(user);
        return jpa.save(entity).toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(UserJpaEntity::toDomain);
    }

    @Override
    public java.util.List<User> findAll() {
        return jpa.findAll().stream()
                .map(UserJpaEntity::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void deleteAll() {
        jpa.deleteAll();
    }

    @Override
    public void saveAll(Iterable<User> users) {
        java.util.List<UserJpaEntity> entities = new java.util.ArrayList<>();
        users.forEach(u -> entities.add(UserJpaEntity.fromDomain(u)));
        jpa.saveAll(entities);
    }
}
