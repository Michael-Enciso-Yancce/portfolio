package com.portfolio.michael.modules.auth.domain;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> findByEmail(String email);

    java.util.List<User> findAll();
}
