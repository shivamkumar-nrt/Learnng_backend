package com.example.demo.modules.fraud;

import com.example.demo.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "risk_alerts", indexes = {
        @Index(name = "idx_risk_severity", columnList = "severity"),
        @Index(name = "idx_risk_status", columnList = "status")
})
public class RiskAlert extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String subject;
    @Column(nullable = false, length = 20)
    private String severity;
    @Column(nullable = false, length = 30)
    private String status;
    @Column(nullable = false, length = 500)
    private String reason;
    @Column(nullable = false, length = 240)
    private String recommendedAction;
}
