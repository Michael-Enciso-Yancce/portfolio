package com.portfolio.michael.modules.contact.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataContactRepository extends JpaRepository<ContactJpaEntity, Long> {
}
