package com.portfolio.michael.modules.skill.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.file.domain.port.FileStoragePort;
import com.portfolio.michael.modules.skill.domain.SkillRepository;
import com.portfolio.michael.modules.skill.application.usecase.CreateSkillUseCase;
import com.portfolio.michael.modules.skill.application.usecase.DeleteSkillUseCase;
import com.portfolio.michael.modules.skill.application.usecase.GetSkillsUseCase;
import com.portfolio.michael.modules.skill.application.usecase.UpdateSkillUseCase;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevelRepository;
import com.portfolio.michael.modules.proficiencylevel.application.usecase.CreateProficiencyLevelUseCase;
import com.portfolio.michael.modules.proficiencylevel.application.usecase.GetProficiencyLevelsUseCase;
import com.portfolio.michael.modules.proficiencylevel.application.usecase.UpdateProficiencyLevelUseCase;
import com.portfolio.michael.modules.proficiencylevel.application.usecase.DeleteProficiencyLevelUseCase;
import com.portfolio.michael.modules.skill.application.usecase.CreateUserSkillUseCase;
import com.portfolio.michael.modules.skill.application.usecase.GetUserSkillsUseCase;
import com.portfolio.michael.modules.skill.application.usecase.UpdateUserSkillUseCase;
import com.portfolio.michael.modules.skill.application.usecase.DeleteUserSkillUseCase;
import com.portfolio.michael.modules.skill.domain.UserSkillRepository;

@Configuration
public class SkillConfiguration {

    @Bean
    public GetSkillsUseCase getSkillsUseCase(SkillRepository skillRepository,
            UserSkillRepository userSkillRepository) {
        return new GetSkillsUseCase(skillRepository, userSkillRepository);
    }

    @Bean
    public CreateSkillUseCase createSkillUseCase(SkillRepository skillRepository,
            FileStoragePort fileStoragePort) {
        return new CreateSkillUseCase(skillRepository, fileStoragePort);
    }

    @Bean
    public UpdateSkillUseCase updateSkillUseCase(SkillRepository skillRepository,
            FileStoragePort fileStoragePort) {
        return new UpdateSkillUseCase(skillRepository, fileStoragePort);
    }

    @Bean
    public DeleteSkillUseCase deleteSkillUseCase(SkillRepository skillRepository, FileStoragePort fileStoragePort) {
        return new DeleteSkillUseCase(skillRepository, fileStoragePort);
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
    public UpdateProficiencyLevelUseCase updateProficiencyLevelUseCase(
            ProficiencyLevelRepository proficiencyLevelRepository) {
        return new UpdateProficiencyLevelUseCase(proficiencyLevelRepository);
    }

    @Bean
    public DeleteProficiencyLevelUseCase deleteProficiencyLevelUseCase(
            ProficiencyLevelRepository proficiencyLevelRepository) {
        return new DeleteProficiencyLevelUseCase(proficiencyLevelRepository);
    }

    @Bean
    public CreateUserSkillUseCase createUserSkillUseCase(UserSkillRepository userSkillRepository,
            SkillRepository skillRepository, ProficiencyLevelRepository proficiencyLevelRepository) {
        return new CreateUserSkillUseCase(userSkillRepository, skillRepository, proficiencyLevelRepository);
    }

    @Bean
    public GetUserSkillsUseCase getUserSkillsUseCase(UserSkillRepository userSkillRepository) {
        return new GetUserSkillsUseCase(userSkillRepository);
    }

    @Bean
    public DeleteUserSkillUseCase deleteUserSkillUseCase(UserSkillRepository userSkillRepository) {
        return new DeleteUserSkillUseCase(userSkillRepository);
    }

    @Bean
    public com.portfolio.michael.modules.skill.application.service.SkillApplicationService skillApplicationService(
            GetSkillsUseCase getSkillsUseCase,
            CreateSkillUseCase createSkillUseCase,
            UpdateSkillUseCase updateSkillUseCase,
            DeleteSkillUseCase deleteSkillUseCase,
            CreateUserSkillUseCase createUserSkillUseCase,
            UpdateUserSkillUseCase updateUserSkillUseCase,
            com.portfolio.michael.modules.auth.domain.UserRepository userRepository,
            UserSkillRepository userSkillRepository) {
        return new com.portfolio.michael.modules.skill.application.service.SkillApplicationService(
                getSkillsUseCase,
                createSkillUseCase,
                updateSkillUseCase,
                deleteSkillUseCase,
                createUserSkillUseCase,
                updateUserSkillUseCase,
                userRepository,
                userSkillRepository);
    }

    @Bean
    public UpdateUserSkillUseCase updateUserSkillUseCase(UserSkillRepository userSkillRepository,
            SkillRepository skillRepository, ProficiencyLevelRepository proficiencyLevelRepository) {
        return new UpdateUserSkillUseCase(userSkillRepository, skillRepository, proficiencyLevelRepository);
    }
}
