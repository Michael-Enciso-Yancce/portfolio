package com.portfolio.michael.modules.projectstatus.infrastructure.controller;

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

import com.portfolio.michael.modules.projectstatus.application.dto.ProjectStatusRequest;
import com.portfolio.michael.modules.projectstatus.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.projectstatus.application.usecase.CreateProjectStatusUseCase;
import com.portfolio.michael.modules.projectstatus.application.usecase.DeleteProjectStatusUseCase;
import com.portfolio.michael.modules.projectstatus.application.usecase.GetProjectStatusesUseCase;
import com.portfolio.michael.modules.projectstatus.application.usecase.UpdateProjectStatusUseCase;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/project-statuses")
@RequiredArgsConstructor
public class ProjectStatusController {

    private final GetProjectStatusesUseCase getProjectStatusesUseCase;
    private final CreateProjectStatusUseCase createProjectStatusUseCase;
    private final UpdateProjectStatusUseCase updateProjectStatusUseCase;
    private final DeleteProjectStatusUseCase deleteProjectStatusUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectStatusResponse>>> getAllProjectStatuses() {
        return ResponseEntity
                .ok(ApiResponse.success("Project statuses retrieved", getProjectStatusesUseCase.execute()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectStatusResponse>> createProjectStatus(
            @Valid @RequestBody ProjectStatusRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Project status created", createProjectStatusUseCase.execute(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectStatusResponse>> updateProjectStatus(
            @PathVariable Long id,
            @Valid @RequestBody ProjectStatusRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Project status updated", updateProjectStatusUseCase.execute(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProjectStatus(@PathVariable Long id) {
        deleteProjectStatusUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Project status deleted", null));
    }
}
