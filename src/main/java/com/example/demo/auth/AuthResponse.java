package com.example.demo.auth;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        String username,
        String displayName,
        UserRole role) {
}