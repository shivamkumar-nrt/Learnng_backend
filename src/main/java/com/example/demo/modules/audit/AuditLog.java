package com.example.demo.modules.audit;

import com.example.demo.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String actorName;

    @Column(nullable = false, length = 120)
    private String actorRole;

    @Column(nullable = false, length = 120)
    private String action;

    @Column(nullable = false, length = 120)
    private String targetType;

    @Column(nullable = false, length = 160)
    private String targetLabel;

    @Column(length = 45)
    private String ipAddress;

    @Column(length = 255)
    private String deviceInfo;

    @Lob
    private String oldValue;

    @Lob
    private String newValue;

    @Column(length = 255)
    private String reason;
}
