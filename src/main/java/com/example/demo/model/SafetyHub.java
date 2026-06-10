package com.example.demo.model;

import java.util.List;

public record SafetyHub(
        List<Metric> guardrails,
        List<String> memberPromises,
        List<String> escalationSteps
) {
}
