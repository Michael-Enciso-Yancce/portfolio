package com.portfolio.michael.modules.catalog.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.catalog.domain.UserSkill;
import com.portfolio.michael.modules.catalog.domain.UserSkillRepository;

@Repository
public class JpaUserSkillRepository implements UserSkillRepository {

    private final SpringDataUserSkillRepository jpa;

    public JpaUserSkillRepository(SpringDataUserSkillRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<UserSkill> findById(Long id) {
        return jpa.findById(id).map(UserSkillJpaEntity::toDomain);
    }

    @Override
    public List<UserSkill> findByUserId(Long userId) {
        return jpa.findByUserId(userId).stream()
                .map(UserSkillJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public UserSkill save(UserSkill userSkill) {
        UserSkillJpaEntity entity = UserSkillJpaEntity.fromDomain(userSkill);
        return jpa.save(entity).toDomain();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
