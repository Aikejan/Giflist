package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.notification.AllNotificationResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "Notification API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationApi {

    private final NotificationService notificationService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping()
    @Operation(summary = "Получить всех уведомления", description = "Вы можете получить все уведомления")
    public AllNotificationResponse getAllNotification() {
        return notificationService.getAllNotification();
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping()
    @Operation(summary = "Метод для просмотрения уведомления")
    public SimpleResponse seenNotify() {
        return notificationService.seenNotify();
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping("/{notificationId}")
    @Operation(summary = "Метод для просмотрения одного уведомления")
    public SimpleResponse seenOneNotify(@PathVariable Long notificationId) {
        return notificationService.seenOneNotify(notificationId);
    }
}