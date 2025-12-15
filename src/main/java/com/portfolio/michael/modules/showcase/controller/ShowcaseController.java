package com.portfolio.michael.modules.showcase.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.modules.showcase.application.dto.CreateShowcaseRequest;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcase;
import com.portfolio.michael.shared.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/showcase")
@RequiredArgsConstructor
public class ShowcaseController {

    private final com.portfolio.michael.modules.showcase.application.service.ShowcaseApplicationService showcaseService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectShowcase>> create(@RequestBody CreateShowcaseRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Showcase created", showcaseService.createShowcase(request)));
    }

    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectShowcase>> update(@PathVariable Long id,
            @RequestBody CreateShowcaseRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Showcase updated", showcaseService.updateShowcase(id, request)));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<ProjectShowcase>> getByProject(@PathVariable Long projectId) {
        return showcaseService.getShowcaseByProjectId(projectId)
                .map(showcase -> ResponseEntity.ok(ApiResponse.success("Showcase retrieved", showcase)))
                .orElse(ResponseEntity.ok(ApiResponse.success("Showcase not found", null)));
    }
}
