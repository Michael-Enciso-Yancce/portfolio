package com.portfolio.michael.modules.project.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.skill.domain.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Long id;
    private User user;
    private ProjectStatus status;
    private String name;
    private String description;
    private String projectUrl;
    private String githubUrl;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();
}
