package com.portfolio.michael.modules.auth.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.auth.application.port.PasswordEncoderPort;
import com.portfolio.michael.modules.auth.application.port.TokenProvider;
import com.portfolio.michael.modules.auth.application.usecase.LoginUseCase;
import com.portfolio.michael.modules.auth.application.usecase.RegisterUseCase;
import com.portfolio.michael.modules.auth.domain.RoleRepository;
import com.portfolio.michael.modules.auth.domain.UserRepository;

@Configuration
public class AuthConfiguration {

    @Bean
    public LoginUseCase loginUseCase(UserRepository userRepository, TokenProvider tokenProvider,
            PasswordEncoderPort passwordEncoder) {
        return new LoginUseCase(userRepository, tokenProvider, passwordEncoder);
    }

    @Bean
    public RegisterUseCase registerUseCase(UserRepository userRepository, RoleRepository roleRepository,
            TokenProvider tokenProvider, PasswordEncoderPort passwordEncoder) {
        return new RegisterUseCase(userRepository, roleRepository, tokenProvider, passwordEncoder);
    }
}
