package com.portfolio.michael.modules.contact.infrastructure.persistence;

import java.time.LocalDateTime;

import com.portfolio.michael.modules.contact.domain.Contact;
import com.portfolio.michael.modules.contact.domain.ContactStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contacts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static ContactJpaEntity fromDomain(Contact domain) {
        return ContactJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .message(domain.getMessage())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public Contact toDomain() {
        return Contact.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .message(this.message)
                .status(this.status)
                .createdAt(this.createdAt)
                .build();
    }
}
