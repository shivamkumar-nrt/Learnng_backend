package com.example.demo.modules.members.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberProfileRequest(
        @NotBlank String memberCode,
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotBlank String phone,
        Integer age,
        String city,
        String state,
        String religion,
        String community,
        String profession,
        String education,
        String annualIncome,
        String intent,
        String bio,
        Boolean verified,
        Integer profileCompletion,
        Boolean active
) {
}
