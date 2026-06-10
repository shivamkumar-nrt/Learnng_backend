package com.example.demo.model;

public record Conversation(
        String id,
        String name,
        String role,
        String lastMessage,
        String status,
        String time,
        int unreadCount
) {
}
