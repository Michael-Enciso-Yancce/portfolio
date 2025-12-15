package com.portfolio.michael.modules.proficiencylevel.infrastructure.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.modules.proficiencylevel.application.dto.ProficiencyLevelRequest;
import com.portfolio.michael.modules.proficiencylevel.application.dto.ProficiencyLevelResponse;
import com.portfolio.michael.modules.proficiencylevel.application.usecase.CreateProficiencyLevelUseCase;
import com.portfolio.michael.modules.proficiencylevel.application.usecase.DeleteProficiencyLevelUseCase;
import com.portfolio.michael.modules.proficiencylevel.application.usecase.GetProficiencyLevelsUseCase;
import com.portfolio.michael.modules.proficiencylevel.application.usecase.UpdateProficiencyLevelUseCase;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/proficiency-levels")
@RequiredArgsConstructor
public class ProficiencyLevelController {

    private final GetProficiencyLevelsUseCase getProficiencyLevelsUseCase;
    private final CreateProficiencyLevelUseCase createProficiencyLevelUseCase;
    private final UpdateProficiencyLevelUseCase updateProficiencyLevelUseCase;
    private final DeleteProficiencyLevelUseCase deleteProficiencyLevelUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProficiencyLevelResponse>>> getAllProficiencyLevels() {
        return ResponseEntity
                .ok(ApiResponse.success("Proficiency levels retrieved", getProficiencyLevelsUseCase.execute()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProficiencyLevelResponse>> createProficiencyLevel(
            @Valid @RequestBody ProficiencyLevelRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Proficiency level created", createProficiencyLevelUseCase.execute(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProficiencyLevelResponse>> updateProficiencyLevel(
            @PathVariable Long id,
            @Valid @RequestBody ProficiencyLevelRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Proficiency level updated",
                        updateProficiencyLevelUseCase.execute(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProficiencyLevel(@PathVariable Long id) {
        deleteProficiencyLevelUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Proficiency level deleted", null));
    }
}
