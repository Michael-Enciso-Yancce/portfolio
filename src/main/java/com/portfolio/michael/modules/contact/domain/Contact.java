package com.portfolio.michael.modules.contact.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private Long id;
    private String name;
    private String email;
    private String message;
    private ContactStatus status;
    private LocalDateTime createdAt;
}
