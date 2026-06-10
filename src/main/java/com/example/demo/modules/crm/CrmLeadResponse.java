package com.example.demo.modules.crm;

public record CrmLeadResponse(
        Long id,
        String memberName,
        String email,
        String city,
        LeadStatus status,
        String relationshipManager,
        String priorityTier,
        String sourceChannel,
        String nextAction
) {
}
