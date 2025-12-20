package com.portfolio.michael.modules.project.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.projectstatus.domain.ProjectStatusRepository;
import com.portfolio.michael.modules.skill.domain.SkillRepository;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;
import com.portfolio.michael.modules.projectstatus.application.usecase.CreateProjectStatusUseCase;
import com.portfolio.michael.modules.project.application.usecase.CreateProjectUseCase;
import com.portfolio.michael.modules.projectstatus.application.usecase.DeleteProjectStatusUseCase;
import com.portfolio.michael.modules.project.application.usecase.DeleteProjectUseCase;
import com.portfolio.michael.modules.projectstatus.application.usecase.GetProjectStatusesUseCase;
import com.portfolio.michael.modules.projectstatus.application.usecase.UpdateProjectStatusUseCase;
import com.portfolio.michael.modules.project.domain.ProjectRepository;

@Configuration
public class ProjectConfiguration {

    @Bean
    public CreateProjectUseCase createProjectUseCase(
            ProjectRepository projectRepository,
            FileStoragePort fileStoragePort,
            UserRepository userRepository,
            ProjectStatusRepository projectStatusRepository,
            SkillRepository skillRepository,
            org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate,
            com.portfolio.michael.modules.showcase.application.usecase.CreateProjectShowcaseUseCase createShowcaseUseCase) {
        return new CreateProjectUseCase(
                projectRepository,
                fileStoragePort,
                userRepository,
                projectStatusRepository,
                skillRepository,
                messagingTemplate,
                createShowcaseUseCase);
    }

    @Bean
    public GetProjectStatusesUseCase getProjectStatusesUseCase(ProjectStatusRepository projectStatusRepository) {
        return new GetProjectStatusesUseCase(projectStatusRepository);
    }

    @Bean
    public CreateProjectStatusUseCase createProjectStatusUseCase(ProjectStatusRepository projectStatusRepository) {
        return new CreateProjectStatusUseCase(projectStatusRepository);
    }

    @Bean
    public DeleteProjectStatusUseCase deleteProjectStatusUseCase(ProjectStatusRepository projectStatusRepository) {
        return new DeleteProjectStatusUseCase(projectStatusRepository);
    }

    @Bean
    public UpdateProjectStatusUseCase updateProjectStatusUseCase(ProjectStatusRepository projectStatusRepository) {
        return new UpdateProjectStatusUseCase(projectStatusRepository);
    }

    @Bean
    public DeleteProjectUseCase deleteProjectUseCase(
            ProjectRepository projectRepository,
            FileStoragePort fileStoragePort,
            org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate) {
        return new DeleteProjectUseCase(projectRepository, fileStoragePort, messagingTemplate);
    }

    @Bean
    public com.portfolio.michael.modules.project.application.service.ProjectApplicationService projectApplicationService(
            CreateProjectUseCase createProjectUseCase,
            com.portfolio.michael.modules.project.application.usecase.GetProjectsUseCase getProjectsUseCase,
            DeleteProjectUseCase deleteProjectUseCase,
            com.portfolio.michael.modules.project.application.usecase.UpdateProjectUseCase updateProjectUseCase) {
        return new com.portfolio.michael.modules.project.application.service.ProjectApplicationService(
                createProjectUseCase,
                getProjectsUseCase,
                deleteProjectUseCase,
                updateProjectUseCase);
    }
}
