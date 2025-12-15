package com.portfolio.michael.modules.dashboard.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private DashboardSummary summary;
    private List<DashboardProject> recentProjects;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardSummary {
        private long totalProjects;
        private long totalSkills;
        private long totalExperience;
        private long totalEducation;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardProject {
        private Long id;
        private String name;
        private String imageUrl;
        private String status;
    }
}
