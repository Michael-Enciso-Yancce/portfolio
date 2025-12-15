package com.portfolio.michael.modules.project.application.usecase;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.project.domain.ProjectStatus;
import com.portfolio.michael.modules.skill.domain.Skill;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.application.dto.UpdateProjectRequest;
import com.portfolio.michael.modules.project.application.mapper.ProjectMapper;
import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final FileStoragePort fileStoragePort;
    private final com.portfolio.michael.modules.project.domain.ProjectStatusRepository statusRepository;
    private final com.portfolio.michael.modules.skill.domain.SkillRepository skillRepository;
    private final org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ProjectResponse execute(Long id, UpdateProjectRequest request) {
        // 1. Fetch existing project
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        // 2. Update fields if not null or present
        if (request.getName() != null) {
            project.setName(request.getName());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getProjectUrl() != null) {
            project.setProjectUrl(request.getProjectUrl());
        }
        if (request.getGithubUrl() != null) {
            project.setGithubUrl(request.getGithubUrl());
        }
        if (request.getStartDate() != null) {
            project.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            project.setEndDate(request.getEndDate());
        }

        // 3. Update related entities if requested
        if (request.getStatusId() != null) {
            ProjectStatus status = statusRepository.findById(request.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Status not found"));
            project.setStatus(status);
        }

        if (request.getSkillIds() != null) {
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(request.getSkillIds()));
            project.setSkills(skills);
        }

        // 4. Update image if provided
        if (request.getImage() != null) {
            String imageUrl = fileStoragePort.upload(request.getImage(), "projects");
            project.setImageUrl(imageUrl);
        }

        // 5. Save (although @Transactional handles it, saving explicitly is fine)
        Project saved = projectRepository.save(project);

        ProjectResponse response = ProjectMapper.toResponse(saved);

        // Broadcast Update
        java.util.Map<String, Object> notification = new java.util.HashMap<>();
        notification.put("type", "UPDATE");
        notification.put("entity", "PROJECT");
        notification.put("data", response);
        messagingTemplate.convertAndSend("/topic/projects", notification);

        return response;
    }
}
