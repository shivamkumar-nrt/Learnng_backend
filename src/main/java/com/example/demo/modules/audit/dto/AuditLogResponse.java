package com.example.demo.modules.audit.dto;

import java.time.Instant;

public record AuditLogResponse(
        Long id,
        String actorName,
        String actorRole,
        String action,
        String targetType,
        String targetLabel,
        String ipAddress,
        String deviceInfo,
        String oldValue,
        String newValue,
        String reason,
        Instant createdAt
) {
}
