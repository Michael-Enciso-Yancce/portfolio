package com.portfolio.michael.modules.auth.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.portfolio.michael.modules.auth.infrastructure.persistence.SpringDataUserRepository;
import com.portfolio.michael.modules.auth.infrastructure.persistence.UserJpaEntity;
import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
        private final SpringDataUserRepository userRepository;

        public CustomUserDetailsService(SpringDataUserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                UserJpaEntity user = userRepository
                                .findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "Usuario no encontrado con email: " + email));

                return new User(
                                user.getEmail(),
                                user.getPassword(),
                                user.getRoles().stream()
                                                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                                                role.getName()))
                                                .collect(java.util.stream.Collectors.toList()));
        }
}
