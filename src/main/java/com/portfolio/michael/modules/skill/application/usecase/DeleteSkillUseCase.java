package com.portfolio.michael.modules.skill.application.usecase;

import com.portfolio.michael.modules.skill.domain.SkillRepository;

public class DeleteSkillUseCase {

    private final SkillRepository skillRepository;
    private final com.portfolio.michael.modules.file.domain.port.FileStoragePort fileStoragePort;

    public DeleteSkillUseCase(SkillRepository skillRepository,
            com.portfolio.michael.modules.file.domain.port.FileStoragePort fileStoragePort) {
        this.skillRepository = skillRepository;
        this.fileStoragePort = fileStoragePort;
    }

    public void execute(Long id) {
        var skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));

        if (skill.getImageUrl() != null && !skill.getImageUrl().isEmpty()) {
            fileStoragePort.delete(skill.getImageUrl());
        }

        skillRepository.deleteById(id);
    }
}
