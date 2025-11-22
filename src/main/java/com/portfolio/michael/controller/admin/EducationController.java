package com.portfolio.michael.controller.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.dto.ApiResponse;
import com.portfolio.michael.dto.admin.request.EducationRequest;
import com.portfolio.michael.dto.admin.response.EducationResponse;
import com.portfolio.michael.entity.Education;
import com.portfolio.michael.mapper.EducationMapper;
import com.portfolio.michael.service.EducationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;
    private final EducationMapper educationMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getAllEducations() {
        List<Education> educations = educationService.getAllEducations();
        List<EducationResponse> responses = educations.stream()
                .map(educationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Educations retrieved successfully", responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> getEducationById(@PathVariable Long id) {
        Education education = educationService.getEducationById(id);
        return ResponseEntity
                .ok(ApiResponse.success("Education retrieved successfully", educationMapper.toResponse(education)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EducationResponse>> createEducation(@RequestBody EducationRequest request) {
        Education education = educationService.createEducation(request);
        return ResponseEntity
                .ok(ApiResponse.success("Education created successfully", educationMapper.toResponse(education)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> updateEducation(@PathVariable Long id,
            @RequestBody EducationRequest request) {
        Education education = educationService.updateEducation(id, request);
        return ResponseEntity
                .ok(ApiResponse.success("Education updated successfully", educationMapper.toResponse(education)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.ok(ApiResponse.success("Education deleted successfully", null));
    }
}
