package com.portfolio.michael.modules.backup.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.modules.backup.application.service.BackupService;
import com.portfolio.michael.shared.dto.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/backup")
@RequiredArgsConstructor
public class BackupController {

    private final BackupService backupService;

    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportBackup(HttpServletResponse response) throws IOException {
        backupService.exportBackup(response);
    }

    @PostMapping("/import")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> importBackup(@RequestParam("file") MultipartFile file) throws IOException {
        backupService.importBackup(file);
        return ResponseEntity.ok(ApiResponse.success("Backup imported successfully", null));
    }
}
