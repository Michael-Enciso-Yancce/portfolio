package com.portfolio.michael.modules.auth.infrastructure.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.portfolio.michael.modules.auth.application.usecase.GoogleLoginUseCase;
import com.portfolio.michael.modules.auth.domain.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final GoogleLoginUseCase googleLoginUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OidcUser oidcUser = (OidcUser) token.getPrincipal();

        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String providerId = oidcUser.getSubject();
        String avatarUrl = oidcUser.getPicture();

        // Process user (create/update)
        User user = googleLoginUseCase.processUser(email, name, providerId, avatarUrl);

        // Generate JWT
        String jwt = jwtTokenProvider.generateToken(user);

        // Redirect to success endpoint (or frontend URL)
        // For simplicity, we redirect to a simple success message, or if frontend is
        // localhost:3000
        // response.sendRedirect("http://localhost:3000?token=" + jwt);

        // Since we don't know exact frontend URL, let's redirect to a local endpoint
        // that shows JSON
        // Or cleaner: redirect to a static success.html or a dedicated endpoint in
        // AuthController that returns JSON view.

        // Redirect to Frontend (React calling from port 5173)
        // Passes the token as a query parameter so React can grab it
        response.sendRedirect("http://localhost:5173/#/oauth/callback?token=" + jwt);
    }
}
