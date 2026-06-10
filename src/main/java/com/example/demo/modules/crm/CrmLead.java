package com.example.demo.modules.crm;

import com.example.demo.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "crm_leads", indexes = {
        @Index(name = "idx_crm_lead_status", columnList = "status"),
        @Index(name = "idx_crm_lead_rm", columnList = "relationshipManager"),
        @Index(name = "idx_crm_lead_priority", columnList = "priorityTier")
})
public class CrmLead extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String memberName;
    @Column(nullable = false, length = 120)
    private String email;
    @Column(nullable = false, length = 60)
    private String city;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LeadStatus status;
    @Column(nullable = false, length = 60)
    private String relationshipManager;
    @Column(nullable = false, length = 30)
    private String priorityTier;
    @Column(nullable = false, length = 120)
    private String sourceChannel;
    @Column(length = 240)
    private String nextAction;
}
