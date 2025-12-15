package com.portfolio.michael.modules.contact.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.portfolio.michael.modules.contact.domain.Contact;
import com.portfolio.michael.modules.contact.domain.ContactRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaContactRepository implements ContactRepository {

    private final SpringDataContactRepository jpaRepository;

    @Override
    public Contact save(Contact contact) {
        return jpaRepository.save(ContactJpaEntity.fromDomain(contact)).toDomain();
    }

    @Override
    public List<Contact> findAll() {
        return jpaRepository.findAll().stream()
                .map(ContactJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return jpaRepository.findById(id).map(ContactJpaEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
