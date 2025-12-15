package com.portfolio.michael.modules.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.modules.dashboard.application.dto.DashboardResponse;
import com.portfolio.michael.modules.dashboard.application.usecase.GetDashboardDataUseCase;
import com.portfolio.michael.shared.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final GetDashboardDataUseCase getDashboardDataUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboardData(java.security.Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Dashboard data retrieved",
                getDashboardDataUseCase.execute(principal.getName())));
    }
}
