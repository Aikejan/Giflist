package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.notification.AllNotificationResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;

public interface NotificationService {
    AllNotificationResponse getAllNotification();
    SimpleResponse seenNotify();
    SimpleResponse seenOneNotify(Long notificationId);
}