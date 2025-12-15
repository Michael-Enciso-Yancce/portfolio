package com.portfolio.michael.modules.dashboard.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.skill.domain.SkillRepository;
import com.portfolio.michael.modules.dashboard.application.dto.DashboardResponse;
import com.portfolio.michael.modules.dashboard.application.dto.DashboardResponse.DashboardProject;
import com.portfolio.michael.modules.dashboard.application.dto.DashboardResponse.DashboardSummary;
import com.portfolio.michael.modules.education.domain.EducationRepository;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;
import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetDashboardDataUseCase {

        private final ProjectRepository projectRepository;
        private final EducationRepository educationRepository;
        private final ExperienceRepository experienceRepository;
        private final SkillRepository skillRepository;

        private final UserRepository userRepository;

        public DashboardResponse execute(String email) {
                // Verify user is authenticated (admin)
                userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                // Summary Counts - Get all records since there's only one admin
                long totalProjects = projectRepository.count();
                long totalExperience = experienceRepository.count();
                long totalEducation = educationRepository.count();
                long totalSkills = skillRepository.count();

                // Recent Projects - Get latest 5 projects
                List<Project> recentProjectsList = projectRepository.findAll().stream()
                                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                                .limit(5)
                                .collect(Collectors.toList());

                List<DashboardProject> recentProjects = recentProjectsList.stream()
                                .map(p -> DashboardProject.builder()
                                                .id(p.getId())
                                                .name(p.getName())
                                                .imageUrl(p.getImageUrl())
                                                .status(p.getStatus() != null ? p.getStatus().getName() : "Unknown")
                                                .build())
                                .collect(Collectors.toList());

                return DashboardResponse.builder()
                                .summary(DashboardSummary.builder()
                                                .totalProjects(totalProjects)
                                                .totalSkills(totalSkills)
                                                .totalExperience(totalExperience)
                                                .totalEducation(totalEducation)
                                                .build())
                                .recentProjects(recentProjects)
                                .build();
        }

}
