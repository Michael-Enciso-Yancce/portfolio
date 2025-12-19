package com.portfolio.michael.modules.publicapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.modules.contact.application.dto.ContactRequest;
import com.portfolio.michael.modules.contact.application.dto.ContactResponse;
import com.portfolio.michael.modules.contact.application.usecase.CreateContactUseCase;
import com.portfolio.michael.modules.education.application.dto.EducationResponse;
import com.portfolio.michael.modules.education.application.usecase.GetEducationsUseCase;
import com.portfolio.michael.modules.experience.application.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.application.usecase.GetExperiencesUseCase;
import com.portfolio.michael.modules.publicapi.application.dto.ProfileResponse;
import com.portfolio.michael.modules.publicapi.application.dto.ProjectPublicResponse;
import com.portfolio.michael.modules.publicapi.application.usecase.GetProfileUseCase;
import com.portfolio.michael.modules.publicapi.application.usecase.GetPublicProjectsUseCase;
import com.portfolio.michael.modules.skill.application.dto.SkillResponse;
import com.portfolio.michael.modules.skill.application.usecase.GetSkillsUseCase;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final GetProfileUseCase getProfileUseCase;
    private final GetPublicProjectsUseCase getPublicProjectsUseCase;
    private final GetEducationsUseCase getEducationsUseCase;
    private final GetExperiencesUseCase getExperiencesUseCase;
    private final GetSkillsUseCase getSkillsUseCase;
    private final CreateContactUseCase createContactUseCase;
    private final com.portfolio.michael.modules.showcase.application.usecase.GetProjectShowcaseUseCase getShowcaseUseCase;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved", getProfileUseCase.execute()));
    }

    @GetMapping("/projects")
    public ResponseEntity<ApiResponse<List<ProjectPublicResponse>>> getProjects() {
        return ResponseEntity.ok(ApiResponse.success("Projects retrieved", getPublicProjectsUseCase.execute()));
    }

    @GetMapping("/showcase/project/{projectId}")
    public ResponseEntity<ApiResponse<com.portfolio.michael.modules.showcase.domain.ProjectShowcase>> getShowcase(
            @PathVariable Long projectId) {
        return getShowcaseUseCase.execute(projectId)
                .map(showcase -> ResponseEntity.ok(ApiResponse.success("Showcase retrieved", showcase)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/educations")
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getEducations() {
        return ResponseEntity.ok(ApiResponse.success("Educations retrieved", getEducationsUseCase.execute()));
    }

    @GetMapping("/experiences")
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getExperiences() {
        return ResponseEntity.ok(ApiResponse.success("Experiences retrieved", getExperiencesUseCase.execute()));
    }

    @GetMapping("/skills")
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getSkills(
            @RequestParam(required = false) String category) {
        String cleanCategory = (category != null && !category.trim().isEmpty()) ? category.trim() : null;
        return ResponseEntity
                .ok(ApiResponse.success("Skills retrieved", getSkillsUseCase.execute(cleanCategory, null)));
    }

    @PostMapping("/contact")
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(
            @Valid @RequestBody ContactRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Message sent successfully", createContactUseCase.execute(request)));
    }
}
