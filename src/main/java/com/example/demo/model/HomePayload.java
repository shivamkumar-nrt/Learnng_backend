package com.example.demo.model;

import java.util.List;

public record HomePayload(
        Hero hero,
        List<Metric> trustMetrics,
        List<String> journeySteps,
        List<MatchProfile> featuredMatches,
        List<SuccessStory> successStories
) {
}
