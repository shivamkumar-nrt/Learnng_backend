package com.example.demo.modules.audit;

import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.persistence.SpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public void log(
            AuditActor actor,
            String action,
            String targetType,
            String targetLabel,
            String oldValue,
            String newValue,
            String reason
    ) {
        AuditLog log = new AuditLog();
        log.setActorName(actor.actorName());
        log.setActorRole(actor.actorRole());
        log.setAction(action);
        log.setTargetType(targetType);
        log.setTargetLabel(targetLabel);
        log.setIpAddress(actor.ipAddress());
        log.setDeviceInfo(actor.deviceInfo());
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setReason(reason);
        auditLogRepository.save(log);
    }

    @Transactional(readOnly = true)
    public Page<AuditLog> search(String q, String action, String actorRole, PageRequestParams params) {
        Specification<AuditLog> specification = Specification
                .where(SpecificationUtils.<AuditLog>multiFieldContains(q, "actorName", "targetLabel", "targetType", "reason"))
                .and(SpecificationUtils.<AuditLog>equalsIgnoreCase("action", action))
                .and(SpecificationUtils.<AuditLog>equalsIgnoreCase("actorRole", actorRole));

        Pageable pageable = PageRequest.of(
                params.safePage(),
                params.safeSize(),
                Sort.by(
                        "asc".equalsIgnoreCase(params.safeSortDir()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                        params.sortBy() == null || params.sortBy().isBlank() ? "createdAt" : params.sortBy()
                )
        );

        return auditLogRepository.findAll(specification, pageable);
    }
}
