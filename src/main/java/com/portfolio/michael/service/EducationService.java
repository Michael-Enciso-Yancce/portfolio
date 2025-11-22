package com.portfolio.michael.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.portfolio.michael.dto.admin.request.EducationRequest;
import com.portfolio.michael.entity.Education;
import com.portfolio.michael.mapper.EducationMapper;
import com.portfolio.michael.repository.EducationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    public List<Education> getAllEducations() {
        return educationRepository.findAll();
    }

    public Education getEducationById(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
    }

    public Education createEducation(EducationRequest request) {
        Education education = educationMapper.toEntity(request);
        return educationRepository.save(education);
    }

    public Education updateEducation(Long id, EducationRequest request) {
        Education education = getEducationById(id);
        educationMapper.updateEntityFromRequest(request, education);
        return educationRepository.save(education);
    }

    public void deleteEducation(Long id) {
        if (!educationRepository.existsById(id)) {
            throw new RuntimeException("Education not found with id: " + id);
        }
        educationRepository.deleteById(id);
    }
}
