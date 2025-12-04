package com.portfolio.michael.modules.education.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.education.application.mapper.EducationMapper;
import com.portfolio.michael.modules.education.application.usecase.CreateEducationUseCase;
import com.portfolio.michael.modules.education.application.usecase.DeleteEducationUseCase;
import com.portfolio.michael.modules.education.application.usecase.GetEducationByIdUseCase;
import com.portfolio.michael.modules.education.application.usecase.GetEducationsUseCase;
import com.portfolio.michael.modules.education.application.usecase.UpdateEducationUseCase;
import com.portfolio.michael.modules.education.domain.EducationRepository;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

@Configuration
public class EducationConfiguration {

    @Bean
    public GetEducationsUseCase getEducationsUseCase(EducationRepository educationRepository,
            EducationMapper educationMapper) {
        return new GetEducationsUseCase(educationRepository, educationMapper);
    }

    @Bean
    public GetEducationByIdUseCase getEducationByIdUseCase(EducationRepository educationRepository,
            EducationMapper educationMapper) {
        return new GetEducationByIdUseCase(educationRepository, educationMapper);
    }

    @Bean
    public CreateEducationUseCase createEducationUseCase(EducationRepository educationRepository,
            UserRepository userRepository, FileStoragePort fileStoragePort, EducationMapper educationMapper) {
        return new CreateEducationUseCase(educationRepository, userRepository, fileStoragePort, educationMapper);
    }

    @Bean
    public UpdateEducationUseCase updateEducationUseCase(EducationRepository educationRepository,
            FileStoragePort fileStoragePort, EducationMapper educationMapper) {
        return new UpdateEducationUseCase(educationRepository, fileStoragePort, educationMapper);
    }

    @Bean
    public DeleteEducationUseCase deleteEducationUseCase(EducationRepository educationRepository,
            FileStoragePort fileStoragePort) {
        return new DeleteEducationUseCase(educationRepository, fileStoragePort);
    }
}
