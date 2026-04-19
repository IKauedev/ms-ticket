package com.example.demo.auth;

import java.util.Locale;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BootstrapUsersInitializer implements ApplicationRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final BootstrapUsersProperties bootstrapUsersProperties;

    public BootstrapUsersInitializer(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            BootstrapUsersProperties bootstrapUsersProperties) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.bootstrapUsersProperties = bootstrapUsersProperties;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!bootstrapUsersProperties.isEnabled()) {
            return;
        }

        createIfMissing(
                bootstrapUsersProperties.getAdminUsername(),
                "Administrator",
                bootstrapUsersProperties.getAdminPassword(),
                UserRole.ADMIN);

        createIfMissing(
                bootstrapUsersProperties.getBoxOfficeUsername(),
                "Box Office",
                bootstrapUsersProperties.getBoxOfficePassword(),
                UserRole.BOX_OFFICE);
    }

    private void createIfMissing(String username, String displayName, String rawPassword, UserRole role) {
        if (isBlank(username) || isBlank(rawPassword)) {
            return;
        }

        String normalizedUsername = username.trim().toLowerCase(Locale.ROOT);
        if (appUserRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            return;
        }

        AppUser user = new AppUser(
                normalizedUsername,
                displayName,
                passwordEncoder.encode(rawPassword),
                role,
                true);

        appUserRepository.save(user);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}