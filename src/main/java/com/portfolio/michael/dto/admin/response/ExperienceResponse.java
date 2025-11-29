package com.portfolio.michael.dto.admin.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponse {
    private Long id;
    private String companyName;
    private String role;
    private String description;
    private String logoUrl;
    private LocalDate startDate;
    private LocalDate endDate;
}
