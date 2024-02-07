package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.dto.notification.AllNotificationResponse;
import com.example.giftlistb10.dto.notification.NotificationResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.entities.Notification;
import com.example.giftlistb10.entities.User;
import com.example.giftlistb10.enums.Request;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.NotificationRepository;
import com.example.giftlistb10.repositories.UserRepository;
import com.example.giftlistb10.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public User getAuthFromUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.getUserByEmail(email).orElseThrow(
                () -> {
                    log.info("User with email: " + email + " not found!");
                    return new NotFoundException(String.format("Пользователь с адресом электронной почты: %s не найден!", email));
                });
    }
    @Override
    public AllNotificationResponse getAllNotification() {
        User user = getAuthFromUser();
        AllNotificationResponse allNotificationResponse = new AllNotificationResponse();
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notify : user.getNotifications()) {
            List<User> fromUsers = notify.getFromUser();
            for (User fromUser : fromUsers) {
                if (notify.getStatus().equals(Status.ADDED_WISH) && notify.getWish() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            notify.getWish().getId()
                    ));
                }
                if (notify.getStatus().equals(Status.ADDED_CHARITY) && notify.getCharity() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            notify.getCharity().getId()
                    ));
                }
                if (notify.getStatus().equals(Status.BOOKED_CHARITY) && notify.getCharity() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            notify.getCharity().getId()
                    ));

                }
                if (notify.getStatus().equals(Status.BOOKED_WISH) && notify.getWish() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            notify.getWish().getId()
                    ));
                }
                if (notify.getStatus().equals(Status.BOOKED_WISH_ANONYMOUSLY) && notify.getWish() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            notify.getWish().getId()
                    ));
                }
                if (notify.getStatus().equals(Status.BOOKED_CHARITY_ANONYMOUSLY) && notify.getWish() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            notify.getCharity().getId()
                    ));
                }
                if (notify.getStatus().equals(Status.UNBOOKED_CHARITY) && notify.getWish() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            notify.getCharity().getId()
                    ));
                }
                if (notify.getStatus().equals(Status.UNBOOKED_WISH) && notify.getWish() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            notify.getWish().getId()
                    ));
                }
                if (notify.getStatus().equals(Status.REQUEST) && fromUser.getRequest() != null) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            fromUser.getId()
                    ));
                }
                if (notify.getRequest() != null && notify.getRequest().equals(Request.ACCEPT_REQUEST)) {
                    notificationResponses.add(new NotificationResponse(
                            notify.getId(),
                            fromUser.getFirstName() + " " + fromUser.getLastName(),
                            fromUser.getUserInfo().getImage(),
                            notify.getStatus(),
                            notify.getLocalDate(),
                            notify.isSeen(),
                            fromUser.getId()
                    ));
                }
            }
        }
        allNotificationResponse.setNotificationResponseList(notificationResponses);
        return allNotificationResponse;
    }

    @Override
    public SimpleResponse seenNotify() {
        User user = getAuthFromUser();
        for (Notification notify : notificationRepository.getAllNotify(user.getEmail())) {
            notify.getSeenUserId().add(user.getId());
            notificationRepository.save(notify);
        }
        log.info("Seen all notifications");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Просмотрено все уведомления")
                .build();
    }

    @Override
    public SimpleResponse seenOneNotify(Long notificationId) {
        User user = getAuthFromUser();
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> {
                    log.info("Notification with notificationId: " + notificationId + " not found!");
                    return new NotFoundException(String.format("Уведомления с идентификатором: %s не найден!", notificationId));
                });
        if(notification.getId().equals(notificationId)){
            notification.getSeenUserId().add(user.getId());
            notification.setSeen(true);
            notificationRepository.save(notification);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Просмотрено уведомления с идентификатором:" +notificationId)
                .build();
    }
}