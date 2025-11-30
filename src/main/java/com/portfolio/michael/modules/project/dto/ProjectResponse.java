package com.portfolio.michael.modules.project.dto;

import java.time.LocalDate;
import java.util.Set;

import com.portfolio.michael.modules.catalog.dto.CatalogResponse;

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
    private CatalogResponse status;
    private String projectUrl;
    private String githubUrl;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<CatalogResponse> skills;
}
