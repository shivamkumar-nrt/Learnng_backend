package com.example.demo.model;

import java.util.List;

public record Hero(
        String badge,
        String title,
        String subtitle,
        List<String> highlights
) {
}
