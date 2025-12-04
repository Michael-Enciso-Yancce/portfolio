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

import com.portfolio.michael.modules.catalog.application.dto.CatalogRequest;
import com.portfolio.michael.modules.catalog.application.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.application.usecase.CreateProficiencyLevelUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.CreateProjectStatusUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.CreateSkillUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.DeleteProficiencyLevelUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.DeleteProjectStatusUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.DeleteSkillUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.GetProficiencyLevelsUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.GetProjectStatusesUseCase;
import com.portfolio.michael.modules.catalog.application.usecase.GetSkillsUseCase;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final GetSkillsUseCase getSkillsUseCase;
    private final CreateSkillUseCase createSkillUseCase;
    private final DeleteSkillUseCase deleteSkillUseCase;

    private final GetProficiencyLevelsUseCase getProficiencyLevelsUseCase;
    private final CreateProficiencyLevelUseCase createProficiencyLevelUseCase;
    private final DeleteProficiencyLevelUseCase deleteProficiencyLevelUseCase;

    private final GetProjectStatusesUseCase getProjectStatusesUseCase;
    private final CreateProjectStatusUseCase createProjectStatusUseCase;
    private final DeleteProjectStatusUseCase deleteProjectStatusUseCase;

    // Skills
    @GetMapping("/skills")
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> getAllSkills() {
        return ResponseEntity.ok(ApiResponse.success("Skills retrieved", getSkillsUseCase.execute()));
    }

    @PostMapping("/skills")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CatalogResponse>> createSkill(@Valid @RequestBody CatalogRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Skill created", createSkillUseCase.execute(request)));
    }

    @DeleteMapping("/skills/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable Long id) {
        deleteSkillUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Skill deleted", null));
    }

    // Proficiency Levels
    @GetMapping("/proficiency-levels")
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> getAllProficiencyLevels() {
        return ResponseEntity
                .ok(ApiResponse.success("Proficiency levels retrieved", getProficiencyLevelsUseCase.execute()));
    }

    @PostMapping("/proficiency-levels")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CatalogResponse>> createProficiencyLevel(
            @Valid @RequestBody CatalogRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Proficiency level created", createProficiencyLevelUseCase.execute(request)));
    }

    @DeleteMapping("/proficiency-levels/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProficiencyLevel(@PathVariable Long id) {
        deleteProficiencyLevelUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Proficiency level deleted", null));
    }

    // Project Statuses
    @GetMapping("/project-statuses")
    public ResponseEntity<ApiResponse<List<CatalogResponse>>> getAllProjectStatuses() {
        return ResponseEntity
                .ok(ApiResponse.success("Project statuses retrieved", getProjectStatusesUseCase.execute()));
    }

    @PostMapping("/project-statuses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CatalogResponse>> createProjectStatus(
            @Valid @RequestBody CatalogRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Project status created", createProjectStatusUseCase.execute(request)));
    }

    @DeleteMapping("/project-statuses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProjectStatus(@PathVariable Long id) {
        deleteProjectStatusUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Project status deleted", null));
    }
}
