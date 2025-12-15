package com.portfolio.michael.modules.contact.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.michael.modules.contact.application.dto.ContactResponse;
import com.portfolio.michael.modules.contact.application.dto.UpdateContactStatusRequest;
import com.portfolio.michael.modules.contact.application.usecase.DeleteContactUseCase;
import com.portfolio.michael.modules.contact.application.usecase.GetContactsUseCase;
import com.portfolio.michael.shared.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final GetContactsUseCase getContactsUseCase;
    private final DeleteContactUseCase deleteContactUseCase;
    private final com.portfolio.michael.modules.contact.application.usecase.UpdateContactStatusUseCase updateContactStatusUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactResponse>>> getAllContacts() {
        return ResponseEntity.ok(ApiResponse.success("Contacts retrieved", getContactsUseCase.execute()));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ContactResponse>> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateContactStatusRequest request) {

        return ResponseEntity.ok(ApiResponse.success("Status updated",
                updateContactStatusUseCase.execute(id, request.getStatus())));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteContact(@PathVariable Long id) {
        deleteContactUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Contact deleted", null));
    }
}
