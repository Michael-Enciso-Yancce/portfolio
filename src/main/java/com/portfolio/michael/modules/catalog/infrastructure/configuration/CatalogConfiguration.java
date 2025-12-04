package com.portfolio.michael.modules.catalog.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.catalog.application.usecase.CreateProficiencyLevelUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.CreateProjectStatusUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.CreateSkillUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.DeleteProficiencyLevelUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.DeleteProjectStatusUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.DeleteSkillUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.GetProficiencyLevelsUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.GetProjectStatusesUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.GetSkillsUseCase;
import com.portfolio.michael.modules.catalog.domain.ProficiencyLevelRepository;
import com.portfolio.michael.modules.catalog.domain.ProjectStatusRepository;
import com.portfolio.michael.modules.catalog.domain.SkillRepository;

@Configuration
public class CatalogConfiguration {

    @Bean
    public GetSkillsUseCase getSkillsUseCase(SkillRepository skillRepository) {
        return new GetSkillsUseCase(skillRepository);
    }

    @Bean
    public CreateSkillUseCase createSkillUseCase(SkillRepository skillRepository) {
        return new CreateSkillUseCase(skillRepository);
    }

    @Bean
    public DeleteSkillUseCase deleteSkillUseCase(SkillRepository skillRepository) {
        return new DeleteSkillUseCase(skillRepository);
    }

    @Bean
    public GetProficiencyLevelsUseCase getProficiencyLevelsUseCase(
            ProficiencyLevelRepository proficiencyLevelRepository) {
        return new GetProficiencyLevelsUseCase(proficiencyLevelRepository);
    }

    @Bean
    public CreateProficiencyLevelUseCase createProficiencyLevelUseCase(
            ProficiencyLevelRepository proficiencyLevelRepository) {
        return new CreateProficiencyLevelUseCase(proficiencyLevelRepository);
    }

    @Bean
    public DeleteProficiencyLevelUseCase deleteProficiencyLevelUseCase(
            ProficiencyLevelRepository proficiencyLevelRepository) {
        return new DeleteProficiencyLevelUseCase(proficiencyLevelRepository);
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
}
