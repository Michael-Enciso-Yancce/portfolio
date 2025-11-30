package com.portfolio.michael.modules.catalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.catalog.dto.CatalogRequest;
import com.portfolio.michael.modules.catalog.dto.CatalogResponse;
import com.portfolio.michael.modules.catalog.entity.ProficiencyLevel;
import com.portfolio.michael.modules.catalog.entity.ProjectStatus;
import com.portfolio.michael.modules.catalog.entity.Skill;
import com.portfolio.michael.modules.catalog.repository.ProficiencyLevelRepository;
import com.portfolio.michael.modules.catalog.repository.ProjectStatusRepository;
import com.portfolio.michael.modules.catalog.repository.SkillRepository;
import com.portfolio.michael.modules.catalog.service.CatalogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final SkillRepository skillRepository;
    private final ProficiencyLevelRepository proficiencyLevelRepository;
    private final ProjectStatusRepository projectStatusRepository;

    // Skills
    @Override
    @Transactional(readOnly = true)
    public List<CatalogResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(s -> new CatalogResponse(s.getId(), s.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CatalogResponse createSkill(CatalogRequest request) {
        Skill skill = Skill.builder().name(request.getName()).build();
        skill = skillRepository.save(skill);
        return new CatalogResponse(skill.getId(), skill.getName());
    }

    @Override
    @Transactional
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    // Proficiency Levels
    @Override
    @Transactional(readOnly = true)
    public List<CatalogResponse> getAllProficiencyLevels() {
        return proficiencyLevelRepository.findAll().stream()
                .map(p -> new CatalogResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CatalogResponse createProficiencyLevel(CatalogRequest request) {
        ProficiencyLevel level = ProficiencyLevel.builder().name(request.getName()).build();
        level = proficiencyLevelRepository.save(level);
        return new CatalogResponse(level.getId(), level.getName());
    }

    @Override
    @Transactional
    public void deleteProficiencyLevel(Long id) {
        proficiencyLevelRepository.deleteById(id);
    }

    // Project Statuses
    @Override
    @Transactional(readOnly = true)
    public List<CatalogResponse> getAllProjectStatuses() {
        return projectStatusRepository.findAll().stream()
                .map(p -> new CatalogResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CatalogResponse createProjectStatus(CatalogRequest request) {
        ProjectStatus status = ProjectStatus.builder().name(request.getName()).build();
        status = projectStatusRepository.save(status);
        return new CatalogResponse(status.getId(), status.getName());
    }

    @Override
    @Transactional
    public void deleteProjectStatus(Long id) {
        projectStatusRepository.deleteById(id);
    }
}
