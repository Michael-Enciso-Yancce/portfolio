package com.portfolio.michael.modules.project.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.project.application.dto.CreateProjectRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.application.dto.ProjectStatusRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectStatusResponse;
import com.portfolio.michael.modules.project.application.dto.UpdateProjectRequest;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final com.portfolio.michael.modules.project.application.service.ProjectApplicationService projectService;

    // --- Project Endpoints ---

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAll() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectResponse> update(
            @PathVariable Long id,
            @RequestPart(value = "name", required = false) String name,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart(value = "statusId", required = false) String statusId,
            @RequestPart(value = "projectUrl", required = false) String projectUrl,
            @RequestPart(value = "githubUrl", required = false) String githubUrl,
            @RequestPart(value = "startDate", required = false) String startDate,
            @RequestPart(value = "endDate", required = false) String endDate,
            @RequestPart(value = "skillIds", required = false) String skillIds,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        FileInput fileInput = null;
        if (image != null && !image.isEmpty()) {
            fileInput = FileInput.builder()
                    .filename(image.getOriginalFilename())
                    .contentType(image.getContentType())
                    .content(image.getInputStream())
                    .size(image.getSize())
                    .build();
        }

        Set<Long> skillIdsSet = null;
        if (skillIds != null && !skillIds.isEmpty()) {
            skillIdsSet = new HashSet<>();
            for (String sId : skillIds.split(",")) {
                skillIdsSet.add(Long.parseLong(sId.trim()));
            }
        }

        UpdateProjectRequest request = UpdateProjectRequest.builder()
                .name(name)
                .description(description)
                .statusId(statusId != null ? Long.parseLong(statusId) : null)
                .projectUrl(projectUrl)
                .githubUrl(githubUrl)
                .startDate(startDate != null ? java.time.LocalDate.parse(startDate) : null)
                .endDate(endDate != null ? java.time.LocalDate.parse(endDate) : null)
                .skillIds(skillIdsSet)
                .image(fileInput)
                .build();

        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectResponse> create(
            @RequestPart("name") String name,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart("statusId") String statusId,
            @RequestPart(value = "projectUrl", required = false) String projectUrl,
            @RequestPart(value = "githubUrl", required = false) String githubUrl,
            @RequestPart(value = "startDate", required = false) String startDate,
            @RequestPart(value = "endDate", required = false) String endDate,
            @RequestPart(value = "skillIds", required = false) String skillIds,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }

        FileInput fileInput = null;
        if (image != null && !image.isEmpty()) {
            fileInput = FileInput.builder()
                    .filename(image.getOriginalFilename())
                    .contentType(image.getContentType())
                    .content(image.getInputStream())
                    .size(image.getSize())
                    .build();
        }

        Set<Long> skillIdsSet = new HashSet<>();
        if (skillIds != null && !skillIds.isEmpty()) {
            for (String id : skillIds.split(",")) {
                skillIdsSet.add(Long.parseLong(id.trim()));
            }
        }

        CreateProjectRequest request = CreateProjectRequest.builder()
                .userId(1L)
                .name(name)
                .description(description)
                .statusId(Long.parseLong(statusId))
                .projectUrl(projectUrl)
                .githubUrl(githubUrl)
                .startDate(startDate != null ? java.time.LocalDate.parse(startDate) : null)
                .endDate(endDate != null ? java.time.LocalDate.parse(endDate) : null)
                .skillIds(skillIdsSet)
                .image(fileInput)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request));
    }

    // --- Project Status Endpoints ---

    @GetMapping("/statuses")
    public ResponseEntity<ApiResponse<List<ProjectStatusResponse>>> getAllProjectStatuses() {
        return ResponseEntity
                .ok(ApiResponse.success("Project statuses retrieved", projectService.getAllProjectStatuses()));
    }

    @PostMapping("/statuses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectStatusResponse>> createProjectStatus(
            @Valid @RequestBody ProjectStatusRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Project status created", projectService.createProjectStatus(request)));
    }

    @PutMapping("/statuses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectStatusResponse>> updateProjectStatus(
            @PathVariable Long id,
            @Valid @RequestBody ProjectStatusRequest request) {
        return ResponseEntity
                .ok(ApiResponse.success("Project status updated", projectService.updateProjectStatus(id, request)));
    }

    @DeleteMapping("/statuses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProjectStatus(@PathVariable Long id) {
        projectService.deleteProjectStatus(id);
        return ResponseEntity.ok(ApiResponse.success("Project status deleted", null));
    }
}
