package com.portfolio.michael.dto.admin.request;

import java.time.LocalDate;

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
    private String logo;
    private LocalDate startDate;
    private LocalDate endDate;
}
