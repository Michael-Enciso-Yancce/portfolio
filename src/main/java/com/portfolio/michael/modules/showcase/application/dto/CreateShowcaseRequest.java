package com.portfolio.michael.modules.showcase.application.dto;

import com.portfolio.michael.modules.showcase.domain.model.ProjectShowcaseContent;

import lombok.Data;

@Data
public class CreateShowcaseRequest {
    private Long projectId;
    private ProjectShowcaseContent content;
    private boolean isCurrent;
}
