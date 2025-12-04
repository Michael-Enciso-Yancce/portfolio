package com.portfolio.michael.modules.project.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.catalog.domain.ProjectStatusRepository;
import com.portfolio.michael.modules.catalog.domain.SkillRepository;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;
import com.portfolio.michael.modules.project.application.usecase.CreateProjectUseCase;
import com.portfolio.michael.modules.project.domain.ProjectRepository;

@Configuration
public class ProjectConfiguration {

    @Bean
    public CreateProjectUseCase createProjectUseCase(
            ProjectRepository projectRepository,
            FileStoragePort fileStoragePort,
            UserRepository userRepository,
            ProjectStatusRepository projectStatusRepository,
            SkillRepository skillRepository) {
        return new CreateProjectUseCase(
                projectRepository,
                fileStoragePort,
                userRepository,
                projectStatusRepository,
                skillRepository);
    }
}
