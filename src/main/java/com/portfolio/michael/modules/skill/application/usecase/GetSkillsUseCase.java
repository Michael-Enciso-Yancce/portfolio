package com.portfolio.michael.modules.skill.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.skill.application.dto.SkillResponse;
import com.portfolio.michael.modules.skill.application.dto.UserSkillResponse;
import com.portfolio.michael.modules.skill.domain.SkillRepository;
import com.portfolio.michael.modules.skill.domain.UserSkill;
import com.portfolio.michael.modules.skill.domain.UserSkillRepository;
import java.util.Map;
import java.util.Collections;
import java.util.function.Function;

public class GetSkillsUseCase {

        private final SkillRepository skillRepository;
        private final UserSkillRepository userSkillRepository;

        public GetSkillsUseCase(SkillRepository skillRepository, UserSkillRepository userSkillRepository) {
                this.skillRepository = skillRepository;
                this.userSkillRepository = userSkillRepository;
        }

        public List<SkillResponse> execute(String category, Long userId) {
                var skills = (category != null && !category.isEmpty())
                                ? skillRepository.findByCategory(category)
                                : skillRepository.findAll();

                Map<Long, UserSkill> userSkillsMap;
                if (userId != null) {
                        userSkillsMap = userSkillRepository.findByUserId(userId).stream()
                                        .collect(Collectors.toMap(us -> us.getSkill().getId(), Function.identity()));
                } else {
                        userSkillsMap = Collections.emptyMap();
                }

                return skills.stream()
                                .map(s -> {
                                        UserSkill userSkill = userSkillsMap.get(s.getId());
                                        UserSkillResponse userSkillResponse = null;

                                        if (userSkill != null) {
                                                userSkillResponse = UserSkillResponse.builder()
                                                                .proficiencyLevelName(userSkill.getProficiencyLevel()
                                                                                .getName())
                                                                .build();
                                        }

                                        return SkillResponse.builder()
                                                        .id(s.getId())
                                                        .name(s.getName())
                                                        .imageUrl(s.getImageUrl())
                                                        .category(s.getCategory())
                                                        .userSkill(userSkillResponse)
                                                        .build();
                                })
                                .collect(Collectors.toList());
        }
}
