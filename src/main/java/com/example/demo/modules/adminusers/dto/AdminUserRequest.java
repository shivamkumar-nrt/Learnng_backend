package com.example.demo.modules.adminusers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record AdminUserRequest(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email @Size(max = 120) String email,
        @NotBlank @Size(max = 20) String phone,
        @NotBlank @Size(min = 8, max = 120) String password,
        @NotBlank @Size(max = 160) String companyName,
        @Size(max = 80) String roleCode,
        @Size(max = 30) String tier,
        Boolean active,
        Set<String> permissions
) {
}
