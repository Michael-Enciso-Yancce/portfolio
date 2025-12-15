package com.portfolio.michael.modules.showcase.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectRepository;
import com.portfolio.michael.modules.showcase.application.dto.CreateShowcaseRequest;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcase;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateProjectShowcaseUseCase {

    private final ProjectShowcaseRepository showcaseRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectShowcase execute(CreateShowcaseRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Integer maxVersion = showcaseRepository.findMaxVersionByProjectId(project.getId()).orElse(0);
        int newVersion = maxVersion + 1;

        // Ensure version in content matches
        request.getContent().setVersion(newVersion);

        // If new one is current, unset previous current
        if (request.isCurrent()) {
            showcaseRepository.findByProjectAndIsCurrentTrue(project).ifPresent(s -> {
                s.setCurrent(false);
                showcaseRepository.save(s);
            });
        }

        ProjectShowcase showcase = ProjectShowcase.builder()
                .project(project)
                .version(newVersion)
                .content(request.getContent())
                .isCurrent(request.isCurrent())
                .build();

        return showcaseRepository.save(showcase);
    }
}
