package com.portfolio.michael.service.admin.impl;

import org.springframework.stereotype.Service;

import com.portfolio.michael.entity.User;
import com.portfolio.michael.repository.UserRepository;
import com.portfolio.michael.service.admin.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User validateUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
