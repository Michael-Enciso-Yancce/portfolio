package com.portfolio.michael.modules.proficiencylevel.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProficiencyLevelResponse {
    private Long id;
    private String name;
}
