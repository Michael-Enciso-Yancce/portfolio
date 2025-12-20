package com.portfolio.michael.modules.project.application.dto;

import java.time.LocalDate;
import java.util.Set;

import com.portfolio.michael.modules.projectstatus.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.skill.application.dto.SkillResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private ProjectStatusResponse status;
    private String projectUrl;
    private String githubUrl;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<SkillResponse> skills;
}
