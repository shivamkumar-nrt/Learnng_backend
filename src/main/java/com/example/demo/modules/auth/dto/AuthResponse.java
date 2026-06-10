package com.example.demo.modules.auth.dto;

public record AuthResponse(
        String accessToken,
        String email,
        String fullName,
        String role,
        String tier,
        java.util.Set<String> permissions
) {
}
