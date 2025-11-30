package com.portfolio.michael.modules.education.service;

import java.util.List;

import com.portfolio.michael.modules.education.dto.EducationRequest;
import com.portfolio.michael.modules.education.dto.EducationResponse;

public interface EducationService {
    List<EducationResponse> getAllEducations();

    EducationResponse getEducationById(Long id);

    EducationResponse createEducation(EducationRequest request);

    EducationResponse updateEducation(Long id, EducationRequest request);

    void deleteEducation(Long id);
}
