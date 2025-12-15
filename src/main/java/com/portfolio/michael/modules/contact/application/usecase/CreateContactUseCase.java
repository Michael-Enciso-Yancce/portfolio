package com.portfolio.michael.modules.contact.application.usecase;

import java.time.LocalDateTime;

import com.portfolio.michael.modules.contact.application.dto.ContactRequest;
import com.portfolio.michael.modules.contact.application.dto.ContactResponse;
import com.portfolio.michael.modules.contact.domain.Contact;
import com.portfolio.michael.modules.contact.domain.ContactRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateContactUseCase {

    private final ContactRepository contactRepository;

    public ContactResponse execute(ContactRequest request) {
        Contact contact = Contact.builder()
                .name(request.getName())
                .email(request.getEmail())
                .message(request.getMessage())
                .status(com.portfolio.michael.modules.contact.domain.ContactStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

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
