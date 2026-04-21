package com.ms.ticket.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "username is required") @Size(min = 3, max = 80, message = "username must have between 3 and 80 characters") String username,
        @NotBlank(message = "displayName is required") @Size(min = 3, max = 120, message = "displayName must have between 3 and 120 characters") String displayName,
        @NotBlank(message = "password is required") @Size(min = 8, max = 120, message = "password must have between 8 and 120 characters") String password) {
}