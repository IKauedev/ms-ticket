package com.ms.ticket.application.dto;

import com.ms.ticket.domain.constants.UserRole;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        String username,
        String displayName,
        UserRole role) {
}