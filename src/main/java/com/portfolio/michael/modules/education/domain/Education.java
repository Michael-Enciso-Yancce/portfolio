package com.portfolio.michael.modules.education.domain;

import java.time.LocalDate;

import com.portfolio.michael.modules.auth.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    private Long id;
    private User user;
    private String institution;
    private String degree;
    private String logoUrl;
    private LocalDate startDate;
    private LocalDate endDate;

    public void update(String institution, String degree, LocalDate startDate, LocalDate endDate) {
        this.institution = institution;
        this.degree = degree;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
