package com.example.demo.model;

import java.util.List;

public record AdminOverview(
        List<Metric> moderationMetrics,
        List<AdminQueueItem> queues,
        List<String> systemPriorities
) {
}
