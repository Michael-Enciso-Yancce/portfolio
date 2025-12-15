package com.portfolio.michael.modules.showcase.domain.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectShowcaseContent implements Serializable {
    private int version;
    private ProblemSection problem;
    private List<String> demonstrates;
    private ArchitectureSection architecture;
    private List<String> flow;
    private List<Decision> decisions;
    private TestSection tests;
    private List<String> future;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProblemSection implements Serializable {
    private String title;
    private String description;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ArchitectureSection implements Serializable {
    private String diagramUrl;
    private String description;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Decision implements Serializable {
    private String title;
    private String rationale;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TestSection implements Serializable {
    private String coverage;
    private List<String> types;
}
