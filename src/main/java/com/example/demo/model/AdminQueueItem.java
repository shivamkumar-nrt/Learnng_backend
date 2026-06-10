package com.example.demo.model;

public record AdminQueueItem(
        String title,
        int count,
        String serviceLevel,
        String note
) {
}
