package com.portfolio.michael.modules.skill.application.service;

import java.util.List;

import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.skill.application.dto.SkillRequest;
import com.portfolio.michael.modules.skill.application.dto.SkillResponse;
import com.portfolio.michael.modules.skill.application.dto.UserSkillRequest;
import com.portfolio.michael.modules.skill.application.dto.UserSkillResponse;
import com.portfolio.michael.modules.skill.application.usecase.CreateSkillUseCase;
import com.portfolio.michael.modules.skill.application.usecase.CreateUserSkillUseCase;
import com.portfolio.michael.modules.skill.application.usecase.DeleteSkillUseCase;
import com.portfolio.michael.modules.skill.application.usecase.GetSkillsUseCase;
import com.portfolio.michael.modules.skill.application.usecase.UpdateSkillUseCase;
import com.portfolio.michael.modules.skill.application.usecase.UpdateUserSkillUseCase;
import com.portfolio.michael.modules.skill.domain.UserSkillRepository;
import com.portfolio.michael.shared.exception.ResourceNotFoundException;

public class SkillApplicationService {

    private final GetSkillsUseCase getSkillsUseCase;
    private final CreateSkillUseCase createSkillUseCase;
    private final UpdateSkillUseCase updateSkillUseCase;
    private final DeleteSkillUseCase deleteSkillUseCase;
    private final CreateUserSkillUseCase createUserSkillUseCase;
    private final UpdateUserSkillUseCase updateUserSkillUseCase;
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    public SkillApplicationService(
            GetSkillsUseCase getSkillsUseCase,
            CreateSkillUseCase createSkillUseCase,
            UpdateSkillUseCase updateSkillUseCase,
            DeleteSkillUseCase deleteSkillUseCase,
            CreateUserSkillUseCase createUserSkillUseCase,
            UpdateUserSkillUseCase updateUserSkillUseCase,
            UserRepository userRepository,
            UserSkillRepository userSkillRepository) {
        this.getSkillsUseCase = getSkillsUseCase;
        this.createSkillUseCase = createSkillUseCase;
        this.updateSkillUseCase = updateSkillUseCase;
        this.deleteSkillUseCase = deleteSkillUseCase;
        this.createUserSkillUseCase = createUserSkillUseCase;
        this.updateUserSkillUseCase = updateUserSkillUseCase;
        this.userRepository = userRepository;
        this.userSkillRepository = userSkillRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<SkillResponse> getAllSkills(Long userId) {
        return getSkillsUseCase.execute(null, userId);
    }

    public SkillResponse createSkill(SkillRequest request, FileInput image, Long userId) {
        SkillResponse response = createSkillUseCase.execute(request, image);

        if (request.getProficiencyLevelId() != null && userId != null) {
            UserSkillRequest userSkillRequest = UserSkillRequest.builder()
                    .skillId(response.getId())
                    .proficiencyLevelId(request.getProficiencyLevelId())
                    .build();
            UserSkillResponse userSkillResponse = createUserSkillUseCase.execute(userId, userSkillRequest);
            response.setUserSkill(userSkillResponse);
        }
        return response;
    }

    public SkillResponse updateSkill(Long id, SkillRequest request, FileInput image, Long userId) {
        SkillResponse response = updateSkillUseCase.execute(id, request, image);

        if (userId != null) {
            UserSkillResponse userSkillResponse = null;
            if (request.getProficiencyLevelId() != null) {
                userSkillResponse = updateUserSkillUseCase.execute(userId, id, request.getProficiencyLevelId());
            } else {
                userSkillResponse = userSkillRepository.findByUserIdAndSkillId(userId, id)
                        .map(us -> UserSkillResponse.builder()
                                .proficiencyLevelName(us.getProficiencyLevel().getName())
                                .build())
                        .orElse(null);
            }
            response.setUserSkill(userSkillResponse);
        }
        return response;
    }

    public void deleteSkill(Long id) {
        deleteSkillUseCase.execute(id);
    }
}
