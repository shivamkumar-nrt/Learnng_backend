package com.example.demo.modules.audit;

import com.example.demo.modules.audit.dto.AuditLogResponse;

public final class AuditLogMapper {

    private AuditLogMapper() {
    }

    public static AuditLogResponse toResponse(AuditLog entity) {
        return new AuditLogResponse(
                entity.getId(),
                entity.getActorName(),
                entity.getActorRole(),
                entity.getAction(),
                entity.getTargetType(),
                entity.getTargetLabel(),
                entity.getIpAddress(),
                entity.getDeviceInfo(),
                entity.getOldValue(),
                entity.getNewValue(),
                entity.getReason(),
                entity.getCreatedAt()
        );
    }
}
