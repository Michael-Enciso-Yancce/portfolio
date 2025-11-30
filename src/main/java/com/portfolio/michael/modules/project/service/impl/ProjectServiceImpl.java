package com.portfolio.michael.modules.project.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.auth.entity.User;
import com.portfolio.michael.modules.auth.repository.UserRepository;
import com.portfolio.michael.modules.catalog.entity.ProjectStatus;
import com.portfolio.michael.modules.catalog.entity.Skill;
import com.portfolio.michael.modules.catalog.repository.ProjectStatusRepository;
import com.portfolio.michael.modules.catalog.repository.SkillRepository;
import com.portfolio.michael.modules.file.service.FileStorageService;
import com.portfolio.michael.modules.project.dto.ProjectRequest;
import com.portfolio.michael.modules.project.dto.ProjectResponse;
import com.portfolio.michael.modules.project.entity.Project;
import com.portfolio.michael.modules.project.mapper.ProjectMapper;
import com.portfolio.michael.modules.project.repository.ProjectRepository;
import com.portfolio.michael.modules.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final SkillRepository skillRepository;
    private final FileStorageService fileStorageService;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return projectMapper.toResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = projectMapper.toEntity(request);

        // Assign User
        project.setUser(getCurrentUser());

        // Assign Status
        ProjectStatus status = projectStatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found"));
        project.setStatus(status);

        // Assign Skills
        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            List<Skill> skills = skillRepository.findAllById(request.getSkillIds());
            project.setSkills(new HashSet<>(skills));
        }

        // Handle Image
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            String imageUrl = fileStorageService.storeFile(request.getImage());
            project.setImageUrl(imageUrl);
        }

        project = projectRepository.save(project);
        return projectMapper.toResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        projectMapper.updateEntityFromRequest(request, project);

        // Update Status
        if (request.getStatusId() != null) {
            ProjectStatus status = projectStatusRepository.findById(request.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Status not found"));
            project.setStatus(status);
        }

        // Update Skills
        if (request.getSkillIds() != null) {
            List<Skill> skills = skillRepository.findAllById(request.getSkillIds());
            project.setSkills(new HashSet<>(skills));
        }

        // Update Image
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            if (project.getImageUrl() != null) {
                fileStorageService.deleteFile(project.getImageUrl());
            }
            String imageUrl = fileStorageService.storeFile(request.getImage());
            project.setImageUrl(imageUrl);
        }

        project = projectRepository.save(project);
        return projectMapper.toResponse(project);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        if (project.getImageUrl() != null) {
            fileStorageService.deleteFile(project.getImageUrl());
        }

        projectRepository.deleteById(id);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
