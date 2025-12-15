package com.portfolio.michael.modules.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.portfolio.michael.modules.auth.application.dto.AuthResponse;
import com.portfolio.michael.modules.auth.application.dto.LoginRequest;
import com.portfolio.michael.modules.auth.application.dto.RegisterRequest;
import com.portfolio.michael.modules.auth.application.dto.UpdateProfileRequest;
import com.portfolio.michael.modules.file.domain.model.FileInput;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final com.portfolio.michael.modules.auth.application.service.AuthApplicationService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/google")
    public void googleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/success")
    public ResponseEntity<?> googleSuccess(@RequestParam("token") String token) {
        return ResponseEntity.ok(AuthResponse.builder().token(token).build());
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(java.security.Principal principal) {
        return ResponseEntity.ok(authService.getCurrentUser(principal.getName()));
    }

    @PutMapping(value = "/profile", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> updateProfile(
            @RequestPart(value = "fullName", required = false) String fullName,
            @RequestPart(value = "title", required = false) String title,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart(value = "email", required = false) String email,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            java.security.Principal principal) throws IOException {

        UpdateProfileRequest request = UpdateProfileRequest.builder()
                .fullName(fullName)
                .title(title)
                .description(description)
                .email(email)
                .build();

        FileInput fileInput = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            fileInput = FileInput.builder()
                    .filename(profileImage.getOriginalFilename())
                    .contentType(profileImage.getContentType())
                    .content(profileImage.getInputStream())
                    .size(profileImage.getSize())
                    .build();
        }

        return ResponseEntity.ok(authService.updateProfile(principal.getName(), request, fileInput));
    }
}
