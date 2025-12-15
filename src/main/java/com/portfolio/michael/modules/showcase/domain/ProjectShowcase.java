package com.portfolio.michael.modules.showcase.domain;

import java.time.LocalDateTime;

import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.showcase.domain.model.ProjectShowcaseContent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectShowcase {
    private Long id;
    private Project project;
    private Integer version;
    private ProjectShowcaseContent content;
    private boolean isCurrent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
