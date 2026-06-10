package com.example.demo.modules.notifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationItemRepository extends JpaRepository<NotificationItem, Long>, JpaSpecificationExecutor<NotificationItem> {
}
