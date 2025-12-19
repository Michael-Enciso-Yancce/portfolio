package com.portfolio.michael.modules.backup.application.dto;

import java.util.List;

import com.portfolio.michael.modules.education.domain.Education;
import com.portfolio.michael.modules.experience.domain.Experience;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevel;
import com.portfolio.michael.modules.project.domain.Project;
import com.portfolio.michael.modules.project.domain.ProjectStatus;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcase;
import com.portfolio.michael.modules.skill.domain.Skill;
import com.portfolio.michael.modules.auth.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackupData {
    private List<User> users;
    private List<ProficiencyLevel> proficiencyLevels;
    private List<Skill> skills;
    private List<ProjectStatus> projectStatuses;
    private List<Project> projects;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<ProjectShowcase> showcases;
}
