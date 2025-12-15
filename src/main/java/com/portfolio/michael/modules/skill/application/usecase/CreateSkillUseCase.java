package com.portfolio.michael.modules.skill.application.usecase;

import com.portfolio.michael.modules.skill.application.dto.SkillRequest;
import com.portfolio.michael.modules.skill.application.dto.SkillResponse;
import com.portfolio.michael.modules.skill.domain.Skill;
import com.portfolio.michael.modules.skill.domain.SkillRepository;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

public class CreateSkillUseCase {

        private final SkillRepository skillRepository;
        private final FileStoragePort fileStoragePort;

        public CreateSkillUseCase(SkillRepository skillRepository, FileStoragePort fileStoragePort) {
                this.skillRepository = skillRepository;
                this.fileStoragePort = fileStoragePort;
        }

        public SkillResponse execute(SkillRequest request, FileInput image) {
                // Subir imagen si está presente
                String imageUrl = null;
                if (image != null) {
                        imageUrl = fileStoragePort.upload(image, "skills");
                }

                Skill skill = Skill.builder()
                                .name(request.getName())
                                .category(request.getCategory())
                                .imageUrl(imageUrl)
                                .build();
                skill = skillRepository.save(skill);
                return SkillResponse.builder()
                                .id(skill.getId())
                                .name(skill.getName())
                                .imageUrl(skill.getImageUrl())
                                .category(skill.getCategory())
                                .build();
        }
}
