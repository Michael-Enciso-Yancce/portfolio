package com.portfolio.michael.modules.project.application.usecase;

import java.util.HashSet;
import java.util.Set;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.catalog.domain.ProjectStatus;
import com.portfolio.michael.modules.catalog.domain.Skill;
import com.portfolio.michael.modules.project.application.dto.CreateProjectRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.application.mapper.ProjectMapper;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;
import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectRepository;

public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final FileStoragePort fileStoragePort;
    private final com.portfolio.michael.modules.auth.domain.UserRepository userRepository;
    private final com.portfolio.michael.modules.catalog.domain.ProjectStatusRepository statusRepository;
    private final com.portfolio.michael.modules.catalog.domain.SkillRepository skillRepository;

    public CreateProjectUseCase(
            ProjectRepository projectRepository,
            FileStoragePort fileStoragePort,
            com.portfolio.michael.modules.auth.domain.UserRepository userRepository,
            com.portfolio.michael.modules.catalog.domain.ProjectStatusRepository statusRepository,
            com.portfolio.michael.modules.catalog.domain.SkillRepository skillRepository) {
        this.projectRepository = projectRepository;
        this.fileStoragePort = fileStoragePort;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.skillRepository = skillRepository;
    }

    public ProjectResponse execute(CreateProjectRequest request) {
        // 1. Upload image if present
        String imageUrl = null;
        if (request.getImage() != null) {
            imageUrl = fileStoragePort.upload(request.getImage(), "projects");
        }

        // 2. Fetch dependencies
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectStatus status = statusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found"));

        Set<Skill> skills = new HashSet<>();
        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            skills = new HashSet<>(skillRepository.findAllById(request.getSkillIds()));
        }

        // 3. Create Domain Entity
        Project project = Project.builder()
                .user(user)
                .status(status)
                .name(request.getName())
                .description(request.getDescription())
                .projectUrl(request.getProjectUrl())
                .githubUrl(request.getGithubUrl())
                .imageUrl(imageUrl)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .skills(skills)
                .build();

        // 4. Save
        Project saved = projectRepository.save(project);

        // 5. Return DTO
        return ProjectMapper.toResponse(saved);
    }
}
