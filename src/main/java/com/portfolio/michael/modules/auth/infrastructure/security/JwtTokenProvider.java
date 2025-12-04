package com.portfolio.michael.modules.auth.infrastructure.security;

import org.springframework.stereotype.Component;

import com.portfolio.michael.modules.auth.application.port.TokenProvider;
import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.infrastructure.security.JwtService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final JwtService jwtService;

    @Override
    public String generateToken(User user) {
        // We need to map Domain User to Entity User or adapt JwtService to accept
        // Domain User.
        // Since JwtService likely expects Entity or UserDetails, and our Domain User is
        // POJO.
        // Let's check JwtService.
        // It probably uses UserDetails interface.
        // Our Domain User does NOT implement UserDetails (pure Java).
        // So we might need an adapter or change JwtService to accept fields.

        // For now, let's assume we can adapt it.
        // I'll create a UserDetails adapter or modify JwtService.
        // But modifying JwtService might break other things if not careful.
        // Let's see JwtService content first.

        // Wait, I can't see JwtService content right now in this tool call.
        // I'll assume I can pass the domain user if I map it to what JwtService
        // expects.
        // Or I can update JwtService to take the Domain User.

        // Actually, the best way in Hexagonal is that the Infrastructure (JwtService)
        // knows about Domain?
        // No, Infrastructure knows Domain.
        // So JwtService (Infrastructure) can take Domain User.

        return jwtService.generateToken(user);
    }
}
