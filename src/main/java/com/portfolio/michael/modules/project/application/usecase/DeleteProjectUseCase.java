package com.portfolio.michael.modules.project.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.project.domain.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteProjectUseCase {

    private final ProjectRepository projectRepository;
    private final com.portfolio.michael.modules.file.domain.port.FileStoragePort fileStoragePort;
    private final org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void execute(Long id) {
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        // Delete image if exists
        if (project.getImageUrl() != null && !project.getImageUrl().isEmpty()) {
            fileStoragePort.delete(project.getImageUrl());
        }

        projectRepository.deleteById(id);

        // Broadcast Delete
        java.util.Map<String, Object> notification = new java.util.HashMap<>();
        notification.put("type", "DELETE");
        notification.put("entity", "PROJECT");
        notification.put("data", id);
        messagingTemplate.convertAndSend("/topic/projects", notification);
    }
}
