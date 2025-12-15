package com.portfolio.michael.modules.project.application.usecase;

import java.util.HashSet;
import java.util.Set;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.project.domain.ProjectStatus;
import com.portfolio.michael.modules.skill.domain.Skill;
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
    private final com.portfolio.michael.modules.project.domain.ProjectStatusRepository statusRepository;
    private final com.portfolio.michael.modules.skill.domain.SkillRepository skillRepository;
    private final org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;
    private final com.portfolio.michael.modules.showcase.application.usecase.CreateProjectShowcaseUseCase createShowcaseUseCase;

    public CreateProjectUseCase(
            ProjectRepository projectRepository,
            FileStoragePort fileStoragePort,
            com.portfolio.michael.modules.auth.domain.UserRepository userRepository,
            com.portfolio.michael.modules.project.domain.ProjectStatusRepository statusRepository,
            com.portfolio.michael.modules.skill.domain.SkillRepository skillRepository,
            org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate,
            com.portfolio.michael.modules.showcase.application.usecase.CreateProjectShowcaseUseCase createShowcaseUseCase) {
        this.projectRepository = projectRepository;
        this.fileStoragePort = fileStoragePort;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.skillRepository = skillRepository;
        this.messagingTemplate = messagingTemplate;
        this.createShowcaseUseCase = createShowcaseUseCase;
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

        // 5. Create Showcase if content is present
        if (request.getShowcaseContent() != null) {
            com.portfolio.michael.modules.showcase.application.dto.CreateShowcaseRequest showcaseRequest = new com.portfolio.michael.modules.showcase.application.dto.CreateShowcaseRequest();
            showcaseRequest.setProjectId(saved.getId());
            showcaseRequest.setContent(request.getShowcaseContent());
            showcaseRequest.setCurrent(true);
            createShowcaseUseCase.execute(showcaseRequest);
        }

        // 5. Return DTO
        ProjectResponse response = ProjectMapper.toResponse(saved);

        // 6. Broadcast Real-time Update
        java.util.Map<String, Object> notification = new java.util.HashMap<>();
        notification.put("type", "CREATE");
        notification.put("entity", "PROJECT");
        notification.put("data", response);
        messagingTemplate.convertAndSend("/topic/projects", notification);

        return response;
    }
}
