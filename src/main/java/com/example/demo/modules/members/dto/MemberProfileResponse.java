package com.example.demo.modules.members.dto;

public record MemberProfileResponse(
        Long id,
        String memberCode,
        String fullName,
        String email,
        String phone,
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
