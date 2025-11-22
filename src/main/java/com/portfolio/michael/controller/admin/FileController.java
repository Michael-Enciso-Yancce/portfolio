package com.portfolio.michael.controller.admin;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.dto.ApiResponse;
import com.portfolio.michael.dto.admin.request.FileRequest;
import com.portfolio.michael.dto.admin.response.FileResponse;
import com.portfolio.michael.entity.File;
import com.portfolio.michael.mapper.FileMapper;
import com.portfolio.michael.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileMapper fileMapper;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<FileResponse>> uploadFile(@ModelAttribute FileRequest request) {
        File savedFile = fileService.saveFile(request.getFile(), request.getRelatedTable(), request.getRelatedId());
        return ResponseEntity.ok(ApiResponse.success("File uploaded successfully", fileMapper.toResponse(savedFile)));
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
