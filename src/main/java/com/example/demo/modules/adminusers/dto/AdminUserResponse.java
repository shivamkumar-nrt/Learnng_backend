package com.example.demo.modules.adminusers.dto;

import java.util.Set;

public record AdminUserResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        String companyName,
        String role,
        Set<String> roles,
        String tier,
        boolean active,
        Set<String> permissions
) {
}
