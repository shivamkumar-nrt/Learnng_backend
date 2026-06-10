package com.example.demo.modules.rbac;

import com.example.demo.modules.audit.AuditActor;
import jakarta.servlet.http.HttpServletRequest;

public final class AuditContextFactory {

    private AuditContextFactory() {
    }

    public static AuditActor from(HttpServletRequest request) {
        String actorName = headerOrDefault(request, "X-Actor-Name", "System Admin");
        String actorRole = headerOrDefault(request, "X-Actor-Role", "SUPER_ADMIN");
        String device = headerOrDefault(request, "User-Agent", "Unknown Device");
        return new AuditActor(actorName, actorRole, request.getRemoteAddr(), device);
    }

    private static String headerOrDefault(HttpServletRequest request, String header, String fallback) {
        String value = request.getHeader(header);
        return value == null || value.isBlank() ? fallback : value;
    }
}
