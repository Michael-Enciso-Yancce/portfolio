package com.portfolio.michael.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.portfolio.michael.dto.ApiResponse;
import com.portfolio.michael.dto.admin.request.EducationRequest;
import com.portfolio.michael.dto.admin.response.EducationResponse;
import com.portfolio.michael.service.admin.EducationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getAllEducations() {
        return ResponseEntity
                .ok(ApiResponse.success("Educations retrieved successfully", educationService.getAllEducations()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> getEducationById(@PathVariable Long id) {
        return ResponseEntity
                .ok(ApiResponse.success("Education retrieved successfully", educationService.getEducationById(id)));
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EducationResponse>> createEducation(
            @Valid @ModelAttribute EducationRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Education created successfully", educationService.createEducation(request)));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EducationResponse>> updateEducation(@PathVariable Long id,
            @Valid @ModelAttribute EducationRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Education updated successfully", educationService.updateEducation(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.ok(ApiResponse.success("Education deleted successfully", null));
    }
}
