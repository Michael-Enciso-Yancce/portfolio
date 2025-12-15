package com.portfolio.michael.modules.proficiencylevel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProficiencyLevel {
    private Long id;
    private String name;
}
