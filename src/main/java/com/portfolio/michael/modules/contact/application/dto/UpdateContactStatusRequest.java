package com.portfolio.michael.modules.contact.application.dto;

import com.portfolio.michael.modules.contact.domain.ContactStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContactStatusRequest {

    @NotNull(message = "Status is required")
    private ContactStatus status;
}
