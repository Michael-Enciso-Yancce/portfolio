package com.portfolio.michael.service.admin.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.dto.admin.request.ProjectRequest;
import com.portfolio.michael.dto.admin.response.ProjectResponse;
import com.portfolio.michael.entity.Project;
import com.portfolio.michael.entity.ProjectStatus;
import com.portfolio.michael.entity.Skill;
import com.portfolio.michael.entity.User;
import com.portfolio.michael.mapper.ProjectMapper;
import com.portfolio.michael.repository.ProjectRepository;
import com.portfolio.michael.repository.ProjectStatusRepository;
import com.portfolio.michael.repository.SkillRepository;
import com.portfolio.michael.repository.UserRepository;
import com.portfolio.michael.helper.FileStorageService;
import com.portfolio.michael.service.admin.ProjectService;

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
