package com.portfolio.michael.modules.skill.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.michael.modules.skill.application.dto.SkillRequest;
import com.portfolio.michael.modules.skill.application.dto.SkillResponse;
import com.portfolio.michael.modules.skill.domain.Skill;
import com.portfolio.michael.modules.skill.domain.SkillRepository;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateSkillUseCase {

    private final SkillRepository skillRepository;
    private final FileStoragePort fileStoragePort;

    @Transactional
    public SkillResponse execute(Long id, SkillRequest request, FileInput image) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));

        // Actualizar campos si están presentes
        if (request.getName() != null) {
            skill.setName(request.getName());
        }
        if (request.getCategory() != null) {
            skill.setCategory(request.getCategory());
        }

        // Actualizar imagen si se proporciona
        if (image != null) {
            String imageUrl = fileStoragePort.upload(image, "skills");
            skill.setImageUrl(imageUrl);
        }

        skill = skillRepository.save(skill);

        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .imageUrl(skill.getImageUrl())
                .category(skill.getCategory())
                .build();
    }
}
