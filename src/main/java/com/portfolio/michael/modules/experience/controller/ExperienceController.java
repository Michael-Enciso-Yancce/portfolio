package com.portfolio.michael.modules.experience.controller;

import java.util.List;

import org.springframework.http.MediaType;
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

import com.portfolio.michael.modules.experience.application.dto.ExperienceRequest;
import com.portfolio.michael.modules.experience.application.dto.ExperienceResponse;
import com.portfolio.michael.modules.experience.application.usecase.CreateExperienceUseCase;
import com.portfolio.michael.modules.experience.application.usecase.DeleteExperienceUseCase;
import com.portfolio.michael.modules.experience.application.usecase.GetExperienceByIdUseCase;
import com.portfolio.michael.modules.experience.application.usecase.GetExperiencesUseCase;
import com.portfolio.michael.modules.experience.application.usecase.UpdateExperienceUseCase;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final GetExperiencesUseCase getExperiencesUseCase;
    private final GetExperienceByIdUseCase getExperienceByIdUseCase;
    private final CreateExperienceUseCase createExperienceUseCase;
    private final UpdateExperienceUseCase updateExperienceUseCase;
    private final DeleteExperienceUseCase deleteExperienceUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getAllExperiences() {
        return ResponseEntity.ok(ApiResponse.success("Experiences retrieved", getExperiencesUseCase.execute()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExperienceResponse>> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Experience retrieved", getExperienceByIdUseCase.execute(id)));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ExperienceResponse>> createExperience(
            @Valid @ModelAttribute ExperienceRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Experience created", createExperienceUseCase.execute(request)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ExperienceResponse>> updateExperience(@PathVariable Long id,
            @Valid @ModelAttribute ExperienceRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Experience updated", updateExperienceUseCase.execute(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteExperience(@PathVariable Long id) {
        deleteExperienceUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Experience deleted", null));
    }
}
