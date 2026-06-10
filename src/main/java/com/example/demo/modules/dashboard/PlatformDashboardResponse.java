package com.example.demo.modules.dashboard;

import java.util.Map;

public record PlatformDashboardResponse(
        Map<String, Long> totals,
        Map<String, Long> crmByStatus,
        Map<String, Long> riskBySeverity
) {
}
