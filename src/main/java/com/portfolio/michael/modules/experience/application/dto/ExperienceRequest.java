package com.portfolio.michael.modules.experience.application.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.validation.ValidFile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequest {
    @NotBlank(message = "Company Name is required")
    private String companyName;

    @NotBlank(message = "Role is required")
    private String role;

    private String description;

    @ValidFile(allowedTypes = { "image/jpeg", "image/png", "image/webp", "image/svg+xml" }, allowedExtensions = { "jpg",
            "jpeg", "png", "webp",
            "svg" }, maxSize = 5 * 1024 * 1024, message = "Image must be JPG, PNG, WEBP or SVG and less than 5MB")
    private MultipartFile logo;

    private LocalDate startDate;
    private LocalDate endDate;
}
