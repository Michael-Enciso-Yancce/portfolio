package com.portfolio.michael.modules.auth.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String fullName;
    private String title;
    private String description;
    private String profileImageUrl;
    private String password;
    private String email;
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
