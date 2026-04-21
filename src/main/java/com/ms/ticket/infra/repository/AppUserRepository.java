package com.ms.ticket.infra.repository;

import java.util.Optional;
import java.util.UUID;

import com.ms.ticket.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);
}