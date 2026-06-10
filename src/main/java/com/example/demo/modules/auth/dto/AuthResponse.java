package com.example.demo.modules.auth.dto;

public record AuthResponse(
        String accessToken,
        String email,
        String fullName,
        String role,
        java.util.Set<String> roles,
        String tier,
        java.util.Set<String> permissions
) {
}
