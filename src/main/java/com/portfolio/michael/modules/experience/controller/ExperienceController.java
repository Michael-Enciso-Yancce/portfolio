package com.portfolio.michael.modules.experience.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.modules.experience.dto.ExperienceRequest;
import com.portfolio.michael.modules.experience.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.service.ExperienceService;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getAllExperiences() {
        return ResponseEntity.ok(ApiResponse.success("Experiences retrieved", experienceService.getAllExperiences()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExperienceResponse>> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Experience retrieved", experienceService.getExperienceById(id)));
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ExperienceResponse>> createExperience(
            @Valid @ModelAttribute ExperienceRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Experience created", experienceService.createExperience(request)));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ExperienceResponse>> updateExperience(@PathVariable Long id,
            @Valid @ModelAttribute ExperienceRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Experience updated", experienceService.updateExperience(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.ok(ApiResponse.success("Experience deleted", null));
    }
}
