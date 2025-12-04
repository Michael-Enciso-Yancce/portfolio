package com.portfolio.michael.modules.project.application.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.portfolio.michael.modules.file.domain.model.FileInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectRequest {
    private Long userId;
    private String name;
    private String description;
    private Long statusId;
    private String projectUrl;
    private String githubUrl;
    private FileInput image;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<Long> skillIds;
}
