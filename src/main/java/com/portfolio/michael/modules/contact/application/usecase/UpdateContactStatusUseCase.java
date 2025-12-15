package com.portfolio.michael.modules.contact.application.usecase;

import com.portfolio.michael.modules.contact.application.dto.ContactResponse;
import com.portfolio.michael.modules.contact.domain.Contact;
import com.portfolio.michael.modules.contact.domain.ContactRepository;
import com.portfolio.michael.modules.contact.domain.ContactStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateContactStatusUseCase {

    private final ContactRepository contactRepository;

    public ContactResponse execute(Long id, ContactStatus status) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        contact.setStatus(status);
        Contact saved = contactRepository.save(contact);

        return ContactResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .message(saved.getMessage())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
