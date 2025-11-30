package com.portfolio.michael.modules.catalog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.shared.dto.ApiResponse;
import com.portfolio.michael.modules.catalog.dto.CatalogRequest;
import com.portfolio.michael.modules.catalog.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.service.CatalogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    // Skills
    @GetMapping("/skills")
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> getAllSkills() {
        return ResponseEntity.ok(ApiResponse.success("Skills retrieved", catalogService.getAllSkills()));
    }

    @PostMapping("/skills")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CatalogResponse>> createSkill(@Valid @RequestBody CatalogRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Skill created", catalogService.createSkill(request)));
    }

    @DeleteMapping("/skills/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable Long id) {
        catalogService.deleteSkill(id);
        return ResponseEntity.ok(ApiResponse.success("Skill deleted", null));
    }

    // Proficiency Levels
    @GetMapping("/proficiency-levels")
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> getAllProficiencyLevels() {
        return ResponseEntity
                .ok(ApiResponse.success("Proficiency levels retrieved", catalogService.getAllProficiencyLevels()));
    }

    @PostMapping("/proficiency-levels")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CatalogResponse>> createProficiencyLevel(
            @Valid @RequestBody CatalogRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Proficiency level created", catalogService.createProficiencyLevel(request)));
    }

    @DeleteMapping("/proficiency-levels/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProficiencyLevel(@PathVariable Long id) {
        catalogService.deleteProficiencyLevel(id);
        return ResponseEntity.ok(ApiResponse.success("Proficiency level deleted", null));
    }

    // Project Statuses
    @GetMapping("/project-statuses")
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> getAllProjectStatuses() {
        return ResponseEntity
                .ok(ApiResponse.success("Project statuses retrieved", catalogService.getAllProjectStatuses()));
    }

    @PostMapping("/project-statuses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CatalogResponse>> createProjectStatus(
            @Valid @RequestBody CatalogRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Project status created", catalogService.createProjectStatus(request)));
    }

    @DeleteMapping("/project-statuses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProjectStatus(@PathVariable Long id) {
        catalogService.deleteProjectStatus(id);
        return ResponseEntity.ok(ApiResponse.success("Project status deleted", null));
    }
}
