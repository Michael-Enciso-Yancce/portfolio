package com.portfolio.michael.service.admin.impl;

import com.portfolio.michael.security.JwtUtil;
import com.portfolio.michael.service.admin.AuthService;
import com.portfolio.michael.dto.admin.request.AuthRequest;
import com.portfolio.michael.dto.admin.response.AuthResponse;
import com.portfolio.michael.entity.User;
import com.portfolio.michael.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthResponse login(AuthRequest request) {
        // ✅ Verificar credenciales
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // ✅ Buscar usuario con if
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // ✅ Generar token
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
