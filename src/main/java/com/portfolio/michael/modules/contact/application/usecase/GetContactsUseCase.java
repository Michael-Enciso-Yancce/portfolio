package com.portfolio.michael.modules.contact.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.michael.modules.contact.application.dto.ContactResponse;
import com.portfolio.michael.modules.contact.domain.ContactRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetContactsUseCase {

    private final ContactRepository contactRepository;

    public List<ContactResponse> execute() {
        return contactRepository.findAll().stream()
                .map(contact -> ContactResponse.builder()
                        .id(contact.getId())
                        .name(contact.getName())
                        .email(contact.getEmail())
                        .message(contact.getMessage())
                        .status(contact.getStatus())
                        .createdAt(contact.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
