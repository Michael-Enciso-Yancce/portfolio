package com.portfolio.michael.modules.auth.application.usecase;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.portfolio.michael.modules.auth.application.dto.AuthResponse;
import com.portfolio.michael.modules.auth.application.dto.GoogleLoginRequest;
import com.portfolio.michael.modules.auth.application.port.TokenProvider;
import com.portfolio.michael.modules.auth.domain.AuthProvider;
import com.portfolio.michael.modules.auth.domain.Role;
import com.portfolio.michael.modules.auth.domain.RoleRepository;
import com.portfolio.michael.modules.auth.domain.User;
import com.portfolio.michael.modules.auth.domain.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class GoogleLoginUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;

    @Value("${spring.security.oauth2.client.registration.google.client-id:YOUR_GOOGLE_CLIENT_ID}")
    private String clientId;

    public GoogleLoginUseCase(UserRepository userRepository, RoleRepository roleRepository,
            TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
    }

    public AuthResponse execute(GoogleLoginRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                    new GsonFactory())
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getToken());
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                User user = processUser(
                        payload.getEmail(),
                        (String) payload.get("name"),
                        payload.getSubject(),
                        (String) payload.get("picture"));
                return AuthResponse.builder()
                        .token(tokenProvider.generateToken(user))
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .roles(user.getRoles().stream().map(role -> role.getName())
                                .collect(java.util.stream.Collectors.toList()))
                        .profileImageUrl(user.getProfileImageUrl())
                        .build();

            } else {
                throw new RuntimeException("Invalid ID token");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Error verifying Google token", e);
        }
    }

    public User processUser(String email, String name, String providerId, String avatarUrl) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));

            User newUser = User.builder()
                    .email(email)
                    .fullName(name)
                    .authProvider(AuthProvider.GOOGLE)
                    .providerId(providerId)
                    .profileImageUrl(avatarUrl)
                    .password("") // No password for OAuth users
                    .roles(Set.of(userRole))
                    .build();
            return userRepository.save(newUser);
        }
    }
}
