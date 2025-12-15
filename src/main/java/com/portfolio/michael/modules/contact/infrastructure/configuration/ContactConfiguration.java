package com.portfolio.michael.modules.contact.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.contact.application.usecase.CreateContactUseCase;
import com.portfolio.michael.modules.contact.application.usecase.DeleteContactUseCase;
import com.portfolio.michael.modules.contact.application.usecase.GetContactsUseCase;
import com.portfolio.michael.modules.contact.application.usecase.UpdateContactStatusUseCase;
import com.portfolio.michael.modules.contact.domain.ContactRepository;

@Configuration
public class ContactConfiguration {

    @Bean
    public CreateContactUseCase createContactUseCase(ContactRepository contactRepository) {
        return new CreateContactUseCase(contactRepository);
    }

    @Bean
    public GetContactsUseCase getContactsUseCase(ContactRepository contactRepository) {
        return new GetContactsUseCase(contactRepository);
    }

    @Bean
    public UpdateContactStatusUseCase updateContactStatusUseCase(ContactRepository contactRepository) {
        return new UpdateContactStatusUseCase(contactRepository);
    }

    @Bean
    public DeleteContactUseCase deleteContactUseCase(ContactRepository contactRepository) {
        return new DeleteContactUseCase(contactRepository);
    }
}
