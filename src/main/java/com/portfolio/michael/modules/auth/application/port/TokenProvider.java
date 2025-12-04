package com.portfolio.michael.modules.auth.application.port;

import com.portfolio.michael.modules.auth.domain.User;

public interface TokenProvider {
    String generateToken(User user);
}
