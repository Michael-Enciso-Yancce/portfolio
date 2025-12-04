package com.portfolio.michael.modules.education.application.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponse {
    private Long id;
    private String institution;
    private String degree;
    private String logoUrl;
    private LocalDate startDate;
    private LocalDate endDate;
}
