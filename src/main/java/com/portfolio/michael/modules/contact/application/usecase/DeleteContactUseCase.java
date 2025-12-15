package com.portfolio.michael.modules.contact.application.usecase;

import com.portfolio.michael.modules.contact.domain.ContactRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteContactUseCase {

    private final ContactRepository contactRepository;

    public void execute(Long id) {
        contactRepository.deleteById(id);
    }
}
