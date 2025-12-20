package com.portfolio.michael.modules.file.controller;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.modules.file.application.usecase.GetFileUseCase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/files")
@RequiredArgsConstructor
public class FileController {

    private final GetFileUseCase getFileUseCase;
    private final com.portfolio.michael.modules.file.application.usecase.UploadFileUseCase uploadFileUseCase;

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = getFileUseCase.execute(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // Fallback to default content type if type could not be determined
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @org.springframework.web.bind.annotation.PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<java.util.Map<String, String>> uploadFile(
            @org.springframework.web.bind.annotation.RequestParam("file") org.springframework.web.multipart.MultipartFile file,
            @org.springframework.web.bind.annotation.RequestParam(value = "folder", defaultValue = "uploads") String folder) {

        try {
            com.portfolio.michael.modules.file.domain.model.FileInput fileInput = com.portfolio.michael.modules.file.domain.model.FileInput
                    .builder()
                    .filename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .content(file.getInputStream())
                    .size(file.getSize())
                    .build();

            String fileUrl = uploadFileUseCase.execute(fileInput, folder);

            return ResponseEntity.ok(java.util.Map.of("url", fileUrl));
        } catch (IOException e) {
            throw new RuntimeException("Failed to process file upload", e);
        }
    }
}
