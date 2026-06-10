package com.example.demo.model;

import java.util.List;

public record ProfileStudio(
        String ownerName,
        int completionPercent,
        List<ProfileChecklistItem> checklist,
        List<String> preferenceSignals,
        List<String> recommendedActions
) {
}
