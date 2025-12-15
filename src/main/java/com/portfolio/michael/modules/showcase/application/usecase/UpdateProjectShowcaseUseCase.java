package com.portfolio.michael.modules.showcase.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.showcase.application.dto.CreateShowcaseRequest;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcase;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProjectShowcaseUseCase {

    private final ProjectShowcaseRepository showcaseRepository;

    @Transactional
    public ProjectShowcase execute(Long id, CreateShowcaseRequest request) {
        ProjectShowcase showcase = showcaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showcase not found"));

        // Update content fields
        showcase.setContent(request.getContent());
        showcase.setCurrent(request.isCurrent());

        // If set to current, unset others
        if (request.isCurrent()) {
            showcaseRepository.findByProjectAndIsCurrentTrue(showcase.getProject()).ifPresent(s -> {
                if (!s.getId().equals(id)) {
                    s.setCurrent(false);
                    showcaseRepository.save(s);
                }
            });
        }

        return showcaseRepository.save(showcase);
    }
}
