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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.project.application.dto.CreateProjectRequest;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.application.dto.UpdateProjectRequest;
import com.portfolio.michael.shared.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final com.portfolio.michael.modules.project.application.service.ProjectApplicationService projectService;

    // --- Project Endpoints ---

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Projects retrieved", projectService.getAllProjects()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success("Project deleted", null));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectResponse>> update(
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

        // Logic:
        // Use @RequestPart because @RequestParam fails for PUT multipart in some
        // containers.
        // Check if this looks like a full update (e.g. "name" is present)
        boolean isFullUpdate = name != null;

        // 1. If part exists (val != null) -> Update to value (empty string if empty)
        // 2. If part Missing (val == null) AND isFullUpdate -> Update to "" (Clear
        // field)
        // 3. If part Missing (val == null) AND !isFullUpdate -> Keep as null (Partial
        // update, ignore field)

        String effectiveDescription = description != null ? description : (isFullUpdate ? "" : null);
        String effectiveProjectUrl = projectUrl != null ? projectUrl : (isFullUpdate ? "" : null);
        String effectiveGithubUrl = githubUrl != null ? githubUrl : (isFullUpdate ? "" : null);

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
                // Name: only update if present and not empty (mandatory field)
                .name(name != null && !name.isEmpty() ? name : null)
                // Optional text fields:
                .description(effectiveDescription)
                .projectUrl(effectiveProjectUrl)
                .githubUrl(effectiveGithubUrl)
                // IDs/Dates: parse if present and not empty, otherwise null (ignore update or
                // clear if logic supported, but usually dates are nullable)
                .statusId(statusId != null && !statusId.isEmpty() ? Long.parseLong(statusId) : null)
                .startDate(startDate != null && !startDate.isEmpty() ? java.time.LocalDate.parse(startDate) : null)
                .endDate(endDate != null && !endDate.isEmpty() ? java.time.LocalDate.parse(endDate) : null)
                .skillIds(skillIdsSet)
                .image(fileInput)
                .build();

        return ResponseEntity
                .ok(ApiResponse.success("Project updated", projectService.updateProject(id, request)));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectResponse>> create(
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

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project created", projectService.createProject(request)));
    }
}
