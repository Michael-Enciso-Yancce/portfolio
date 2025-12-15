package com.portfolio.michael.modules.showcase.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectRepository;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcase;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetProjectShowcaseUseCase {

    private final ProjectShowcaseRepository showcaseRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public Optional<ProjectShowcase> execute(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return showcaseRepository.findByProjectAndIsCurrentTrue(project);
    }
}
