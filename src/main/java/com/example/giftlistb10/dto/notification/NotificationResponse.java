package com.example.giftlistb10.dto.notification;

import com.example.giftlistb10.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NotificationResponse {
    private Long notificationId;
    private String fullName;
    private String image;
    private Status status;;
    private LocalDate createdAt;
    private boolean seen;
    private Long basketId;

    public NotificationResponse(Long notificationId, String fullName, String image, Status status, LocalDate createdAt, boolean seen, Long basketId) {
        this.notificationId = notificationId;
        this.fullName = fullName;
        this.image = image;
        this.status = status;
        this.createdAt = createdAt;
        this.seen = seen;
        this.basketId = basketId;
    }
}



