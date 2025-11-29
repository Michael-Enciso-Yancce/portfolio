package com.portfolio.michael.service.admin;

import java.util.List;

import com.portfolio.michael.dto.admin.request.EducationRequest;
import com.portfolio.michael.dto.admin.response.EducationResponse;

public interface EducationService {
    List<EducationResponse> getAllEducations();

    EducationResponse getEducationById(Long id);

    EducationResponse createEducation(EducationRequest request);

    EducationResponse updateEducation(Long id, EducationRequest request);

    void deleteEducation(Long id);
}
