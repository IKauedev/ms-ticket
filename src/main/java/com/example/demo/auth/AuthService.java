package com.example.demo.auth;

import java.util.Collection;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserProfileResponse register(RegisterRequest request) {
        String normalizedUsername = normalizeUsername(request.username());
        if (appUserRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        AppUser user = new AppUser(
                normalizedUsername,
                request.displayName().trim(),
                passwordEncoder.encode(request.password()),
                UserRole.CUSTOMER,
                true);

        AppUser savedUser = appUserRepository.save(user);
        return new UserProfileResponse(savedUser.getUsername(), savedUser.getDisplayName(), savedUser.getRole());
    }

    // Login agora é feito via Keycloak/OIDC, não local
    // O endpoint /login redireciona para o fluxo OAuth2

    public UserProfileResponse currentUser(Authentication authentication) {
        // Para OIDC, extrai claims do token
        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.jwt.Jwt jwt) {
            String username = jwt.getClaimAsString("preferred_username");
            String displayName = jwt.getClaimAsString("name");
            String role = jwt.getClaimAsStringList("roles") != null && !jwt.getClaimAsStringList("roles").isEmpty()
                    ? jwt.getClaimAsStringList("roles").get(0)
                    : "CUSTOMER";
            return new UserProfileResponse(username, displayName, UserRole.valueOf(role));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication");
    }

    // extração de role agora é feita via claims do token OIDC

    private String normalizeUsername(String username) {
        return username.trim().toLowerCase(Locale.ROOT);
    }
}