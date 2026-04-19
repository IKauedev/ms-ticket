package com.example.demo.auth;

public record UserProfileResponse(String username, String displayName, UserRole role) {
}