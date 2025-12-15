package com.portfolio.michael.modules.publicapi.application.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPublicResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String projectUrl;
    private String githubUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<SkillInfo> skills;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkillInfo {
        private Long id;
        private String name;
        private String category;
        private String imageUrl;
    }
}
