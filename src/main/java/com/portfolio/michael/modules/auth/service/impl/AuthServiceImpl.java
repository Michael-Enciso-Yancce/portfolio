package com.portfolio.michael.modules.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.michael.modules.auth.dto.AuthResponse;
import com.portfolio.michael.modules.auth.dto.LoginRequest;
import com.portfolio.michael.modules.auth.dto.RegisterRequest;
import com.portfolio.michael.modules.auth.entity.Role;
import com.portfolio.michael.modules.auth.entity.User;
import com.portfolio.michael.modules.auth.repository.RoleRepository;
import com.portfolio.michael.modules.auth.repository.UserRepository;
import com.portfolio.michael.modules.auth.security.JwtService;
import com.portfolio.michael.modules.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;

        @Override
        public AuthResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                String token = jwtService.generateToken(user);

                return AuthResponse.builder()
                                .token(token)
                                .build();
        }

        @Override
        public AuthResponse register(RegisterRequest request) {
                if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                        throw new RuntimeException("Email already exists");
                }

                Role userRole = roleRepository.findByName("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Default role not found"));

                User user = User.builder()
                                .fullName(request.getFullName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .roles(java.util.Set.of(userRole))
                                .build();

                userRepository.save(user);

                String token = jwtService.generateToken(user);

                return AuthResponse.builder()
                                .token(token)
                                .build();
        }
}
