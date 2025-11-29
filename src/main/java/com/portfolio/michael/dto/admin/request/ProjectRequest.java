package com.portfolio.michael.dto.admin.request;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.validation.ValidFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Status ID is required")
    private Long statusId;

    private String projectUrl;
    private String githubUrl;

    @ValidFile(allowedTypes = { "image/jpeg", "image/png", "image/webp" }, allowedExtensions = { "jpg", "jpeg", "png",
            "webp" }, maxSize = 5 * 1024 * 1024, message = "Image must be JPG, PNG, or WEBP and less than 5MB")
    private MultipartFile image;

    private LocalDate startDate;
    private LocalDate endDate;

    private Set<Long> skillIds;
}
