package com.portfolio.michael.dto.admin.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
