package com.portfolio.michael.modules.publicapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private String fullName;
    private String title;
    private String description;
    private String profileImageUrl;
    private String email;
}
