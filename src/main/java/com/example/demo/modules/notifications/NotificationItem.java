package com.example.demo.modules.notifications;

import com.example.demo.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification_items", indexes = {
        @Index(name = "idx_notification_channel", columnList = "channel"),
        @Index(name = "idx_notification_category", columnList = "category")
})
public class NotificationItem extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String title;
    @Column(nullable = false, length = 500)
    private String body;
    @Column(nullable = false, length = 40)
    private String channel;
    @Column(nullable = false, length = 60)
    private String category;
    @Column(nullable = false)
    private Boolean active;
}
