package com.portfolio.michael.modules.education.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.validation.ValidFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequest {
    private String institution;
    private String degree;
    @ValidFile(allowedTypes = { "image/jpeg", "image/png", "image/webp", "image/svg+xml" }, allowedExtensions = { "jpg",
            "jpeg", "png", "webp", "svg" }, maxSize = 5 * 1024 * 1024, // 5MB
            message = "El archivo debe ser una imagen (JPG, PNG, WEBP, SVG) y menor a 5MB")
    private MultipartFile file;
    private LocalDate startDate;
    private LocalDate endDate;
}
