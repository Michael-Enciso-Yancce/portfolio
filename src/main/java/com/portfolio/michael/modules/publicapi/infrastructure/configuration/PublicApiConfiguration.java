package com.portfolio.michael.modules.publicapi.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.project.domain.ProjectRepository;
import com.portfolio.michael.modules.publicapi.application.usecase.GetProfileUseCase;
import com.portfolio.michael.modules.publicapi.application.usecase.GetPublicProjectsUseCase;

@Configuration
public class PublicApiConfiguration {

    @Bean
    public GetProfileUseCase getProfileUseCase(UserRepository userRepository) {
        return new GetProfileUseCase(userRepository);
    }

    @Bean
    public GetPublicProjectsUseCase getPublicProjectsUseCase(ProjectRepository projectRepository) {
        return new GetPublicProjectsUseCase(projectRepository);
    }
}
