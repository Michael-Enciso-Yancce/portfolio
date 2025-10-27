package com.portfolio.michael.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.portfolio.michael.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
