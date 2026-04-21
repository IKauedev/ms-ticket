package com.ms.ticket.application.dto;

import com.ms.ticket.domain.constants.UserRole;

public record UserProfileResponse(String username, String displayName, UserRole role) {
}