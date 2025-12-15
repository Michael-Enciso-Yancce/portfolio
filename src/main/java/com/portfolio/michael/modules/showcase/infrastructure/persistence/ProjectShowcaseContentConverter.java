package com.portfolio.michael.modules.showcase.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.michael.modules.showcase.domain.model.ProjectShowcaseContent;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProjectShowcaseContentConverter implements AttributeConverter<ProjectShowcaseContent, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ProjectShowcaseContent attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting ProjectShowcaseContent to JSON", e);
        }
    }

    @Override
    public ProjectShowcaseContent convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, ProjectShowcaseContent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to ProjectShowcaseContent", e);
        }
    }
}
