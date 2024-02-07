package com.example.giftlistb10.dto.notification;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AllNotificationResponse {
   private List<NotificationResponse> notificationResponseList;
}