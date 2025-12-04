package com.portfolio.michael.modules.experience.domain;

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
public class Experience {
    private Long id;
    private User user;
    private String companyName;
    private String role;
    private String description;
    private String logoUrl;
    private LocalDate startDate;
    private LocalDate endDate;

    public void update(String companyName, String role, String description, LocalDate startDate, LocalDate endDate) {
        this.companyName = companyName;
        this.role = role;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
