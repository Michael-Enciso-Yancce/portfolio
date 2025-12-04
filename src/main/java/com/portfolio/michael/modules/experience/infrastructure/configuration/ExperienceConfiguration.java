package com.portfolio.michael.modules.experience.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.experience.application.mapper.ExperienceMapper;
import com.portfolio.michael.modules.experience.application.usecase.CreateExperienceUseCase;
import com.portfolio.michael.modules.experience.application.usecase.DeleteExperienceUseCase;
import com.portfolio.michael.modules.experience.application.usecase.GetExperienceByIdUseCase;
import com.portfolio.michael.modules.experience.application.usecase.GetExperiencesUseCase;
import com.portfolio.michael.modules.experience.application.usecase.UpdateExperienceUseCase;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

@Configuration
public class ExperienceConfiguration {

    @Bean
    public GetExperiencesUseCase getExperiencesUseCase(ExperienceRepository experienceRepository,
            ExperienceMapper experienceMapper) {
        return new GetExperiencesUseCase(experienceRepository, experienceMapper);
    }

    @Bean
    public GetExperienceByIdUseCase getExperienceByIdUseCase(ExperienceRepository experienceRepository,
            ExperienceMapper experienceMapper) {
        return new GetExperienceByIdUseCase(experienceRepository, experienceMapper);
    }

    @Bean
    public CreateExperienceUseCase createExperienceUseCase(ExperienceRepository experienceRepository,
            UserRepository userRepository, FileStoragePort fileStoragePort, ExperienceMapper experienceMapper) {
        return new CreateExperienceUseCase(experienceRepository, userRepository, fileStoragePort, experienceMapper);
    }

    @Bean
    public UpdateExperienceUseCase updateExperienceUseCase(ExperienceRepository experienceRepository,
            FileStoragePort fileStoragePort, ExperienceMapper experienceMapper) {
        return new UpdateExperienceUseCase(experienceRepository, fileStoragePort, experienceMapper);
    }

    @Bean
    public DeleteExperienceUseCase deleteExperienceUseCase(ExperienceRepository experienceRepository,
            FileStoragePort fileStoragePort) {
        return new DeleteExperienceUseCase(experienceRepository, fileStoragePort);
    }
}
