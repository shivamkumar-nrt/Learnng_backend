package com.example.demo.modules.fraud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RiskAlertRepository extends JpaRepository<RiskAlert, Long>, JpaSpecificationExecutor<RiskAlert> {
}
