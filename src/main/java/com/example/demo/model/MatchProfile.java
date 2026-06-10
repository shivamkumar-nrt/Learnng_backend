package com.example.demo.model;

import java.util.List;

public record MatchProfile(
        String id,
        String name,
        int age,
        String location,
        String profession,
        String education,
        String community,
        String faith,
        String height,
        String annualIncome,
        String bio,
        int matchScore,
        String intent,
        String availability,
        List<String> tags,
        List<String> reasons
) {
}
