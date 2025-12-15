package com.portfolio.michael.modules.proficiencylevel.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProficiencyLevelRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
