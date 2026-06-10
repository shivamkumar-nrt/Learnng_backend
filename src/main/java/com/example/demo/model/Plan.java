package com.example.demo.model;

import java.util.List;

public record Plan(
        String name,
        String price,
        String cadence,
        boolean featured,
        boolean dark,
        List<String> features
) {
}
