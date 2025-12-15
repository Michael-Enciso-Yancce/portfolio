package com.portfolio.michael.modules.skill.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.skill.application.dto.SkillRequest;
import com.portfolio.michael.modules.skill.application.dto.SkillResponse;
import com.portfolio.michael.shared.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/skills")
@RequiredArgsConstructor
public class SkillController {

    private final com.portfolio.michael.modules.skill.application.service.SkillApplicationService skillService;

    private User getUser(UserDetails userDetails) {
        if (userDetails == null)
            return null;
        return skillService.getUserByEmail(userDetails.getUsername());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAllSkills(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = getUser(userDetails);
        Long userId = user != null ? user.getId() : null;
        return ResponseEntity.ok(ApiResponse.success("Skills retrieved", skillService.getAllSkills(userId)));
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SkillResponse>> createSkill(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestPart("name") String name,
            @RequestPart(value = "category", required = false) String category,
            @RequestPart(value = "proficiencyLevelId", required = false) String proficiencyLevelId,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        User user = getUser(userDetails);

        SkillRequest request = SkillRequest.builder()
                .name(name)
                .category(category)
                .proficiencyLevelId(proficiencyLevelId != null && !proficiencyLevelId.equals("null")
                        ? Long.parseLong(proficiencyLevelId)
                        : null)
                .build();

        FileInput fileInput = null;
        if (image != null && !image.isEmpty()) {
            fileInput = FileInput.builder()
                    .filename(image.getOriginalFilename())
                    .contentType(image.getContentType())
                    .content(image.getInputStream())
                    .size(image.getSize())
                    .build();
        }

        SkillResponse skillResponse = skillService.createSkill(request, fileInput, user != null ? user.getId() : null);

        return ResponseEntity.ok(ApiResponse.success("Skill created", skillResponse));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SkillResponse>> updateSkill(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestPart(value = "name", required = false) String name,
            @RequestPart(value = "category", required = false) String category,
            @RequestPart(value = "proficiencyLevelId", required = false) String proficiencyLevelId,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        User user = getUser(userDetails);

        SkillRequest request = SkillRequest.builder()
                .name(name)
                .category(category)
                .proficiencyLevelId(proficiencyLevelId != null && !proficiencyLevelId.isEmpty()
                        && !proficiencyLevelId.equals("null") ? Long.parseLong(proficiencyLevelId) : null)
                .build();

        FileInput fileInput = null;
        if (image != null && !image.isEmpty()) {
            fileInput = FileInput.builder()
                    .filename(image.getOriginalFilename())
                    .contentType(image.getContentType())
                    .content(image.getInputStream())
                    .size(image.getSize())
                    .build();
        }

        SkillResponse skillResponse = skillService.updateSkill(id, request, fileInput,
                user != null ? user.getId() : null);

        return ResponseEntity.ok(ApiResponse.success("Skill updated", skillResponse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok(ApiResponse.success("Skill deleted", null));
    }
}
