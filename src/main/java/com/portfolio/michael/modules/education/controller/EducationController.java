package com.portfolio.michael.modules.education.controller;

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

import com.portfolio.michael.modules.education.application.dto.EducationRequest;
import com.portfolio.michael.modules.education.application.dto.EducationResponse;
import com.portfolio.michael.modules.education.application.usecase.CreateEducationUseCase;
import com.portfolio.michael.modules.education.application.usecase.DeleteEducationUseCase;
import com.portfolio.michael.modules.education.application.usecase.GetEducationByIdUseCase;
import com.portfolio.michael.modules.education.application.usecase.GetEducationsUseCase;
import com.portfolio.michael.modules.education.application.usecase.UpdateEducationUseCase;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/educations")
@RequiredArgsConstructor
public class EducationController {

    private final GetEducationsUseCase getEducationsUseCase;
    private final GetEducationByIdUseCase getEducationByIdUseCase;
    private final CreateEducationUseCase createEducationUseCase;
    private final UpdateEducationUseCase updateEducationUseCase;
    private final DeleteEducationUseCase deleteEducationUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getAllEducations() {
        return ResponseEntity.ok(ApiResponse.success("Educations retrieved", getEducationsUseCase.execute()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> getEducationById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Education retrieved", getEducationByIdUseCase.execute(id)));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EducationResponse>> createEducation(
            @Valid @ModelAttribute EducationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Education created", createEducationUseCase.execute(request)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EducationResponse>> updateEducation(@PathVariable Long id,
            @Valid @ModelAttribute EducationRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Education updated", updateEducationUseCase.execute(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEducation(@PathVariable Long id) {
        deleteEducationUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Education deleted", null));
    }
}
