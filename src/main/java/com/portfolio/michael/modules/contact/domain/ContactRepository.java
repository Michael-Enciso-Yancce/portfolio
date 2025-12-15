package com.portfolio.michael.modules.contact.domain;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    Contact save(Contact contact);

    List<Contact> findAll();

    Optional<Contact> findById(Long id);

    void deleteById(Long id);

    long count();
}
