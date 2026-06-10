package com.example.demo.modules.audit;

public record AuditActor(
        String actorName,
        String actorRole,
        String ipAddress,
        String deviceInfo
) {
}
