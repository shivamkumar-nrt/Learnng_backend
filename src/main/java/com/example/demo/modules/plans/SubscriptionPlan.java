package com.example.demo.modules.plans;

import com.example.demo.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subscription_plans", indexes = {
        @Index(name = "idx_plan_name", columnList = "name", unique = true)
})
public class SubscriptionPlan extends BaseEntity {

    @Column(nullable = false, unique = true, length = 40)
    private String name;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer durationMonths;
    @Column(nullable = false, length = 600)
    private String featureSummary;
    @Column(nullable = false)
    private Boolean featured;
}
