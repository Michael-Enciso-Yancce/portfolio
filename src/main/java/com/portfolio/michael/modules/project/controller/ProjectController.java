package com.portfolio.michael.modules.project.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.modules.project.application.dto.CreateProjectRequest;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.project.application.dto.ProjectResponse;
import com.portfolio.michael.modules.project.application.usecase.CreateProjectUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
                .userId(1L) // TODO: Get from security context
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

        return ResponseEntity.status(HttpStatus.CREATED).body(createProjectUseCase.execute(request));
    }
}
