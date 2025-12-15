package com.portfolio.michael.modules.showcase.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.showcase.application.usecase.CreateProjectShowcaseUseCase;
import com.portfolio.michael.modules.showcase.application.usecase.GetProjectShowcaseUseCase;
import com.portfolio.michael.modules.showcase.application.usecase.UpdateProjectShowcaseUseCase;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcaseRepository;
import com.portfolio.michael.modules.project.domain.ProjectRepository;

@Configuration
public class ShowcaseConfiguration {

    @Bean
    public CreateProjectShowcaseUseCase createProjectShowcaseUseCase(
            ProjectShowcaseRepository repository,
            ProjectRepository projectRepository) {
        return new CreateProjectShowcaseUseCase(repository, projectRepository);
    }

    @Bean
    public GetProjectShowcaseUseCase getProjectShowcaseUseCase(ProjectShowcaseRepository repository,
            ProjectRepository projectRepository) {
        return new GetProjectShowcaseUseCase(repository, projectRepository);
    }

    @Bean
    public UpdateProjectShowcaseUseCase updateProjectShowcaseUseCase(ProjectShowcaseRepository repository) {
        return new UpdateProjectShowcaseUseCase(repository);
    }

    @Bean
    public com.portfolio.michael.modules.showcase.application.service.ShowcaseApplicationService showcaseApplicationService(
            CreateProjectShowcaseUseCase createUseCase,
            GetProjectShowcaseUseCase getUseCase,
            UpdateProjectShowcaseUseCase updateUseCase) {
        return new com.portfolio.michael.modules.showcase.application.service.ShowcaseApplicationService(
                createUseCase,
                getUseCase,
                updateUseCase);
    }
}
