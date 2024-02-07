package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.dto.myFriends.MyFriendsResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.entities.*;
import com.example.giftlistb10.enums.Request;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.exceptions.AlreadyExistException;
import com.example.giftlistb10.exceptions.BadCredentialException;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.*;
import com.example.giftlistb10.repositories.jdbcTemplate.UserJDBCTemplate;
import com.example.giftlistb10.services.MyFriendsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyFriendsServiceImpl implements MyFriendsService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final UserJDBCTemplate userJDBCTemplate;

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
    public List<MyFriendsResponse> getAllUserRequest() {
        User user = getAuthFromUser();
        return userJDBCTemplate.getAllUserRequest(user.getId());
    }

    @Override
    public SimpleResponse sendRequestToFriend(Long userId) {
        User requestSender = getAuthFromUser();
        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    log.info("User with userId: " + userId + " not found!");
                    return new NotFoundException(String.format("Пользователь с идентификатором: %s не найден!", userId));
                });
        if (user.getRequest().contains(requestSender)) {
            log.info("User already exists in your friend list");
            throw new AlreadyExistException("Пользователь уже существует в вашем списке друзей");
        }
        if (requestSender.getId().equals(user.getId())) {
            log.info("You have not permission sent request yourself");
            throw new BadCredentialException("У вас нет разрешения отправлять запрос самому себе");
        }
        Notification notification = new Notification();
        notification.setLocalDate(LocalDate.now());
        notification.setStatus(Status.REQUEST);
        notification.setSeen(false);
        List<User> fromUser = new ArrayList<>();
        fromUser.add(requestSender);
        notification.setFromUser(fromUser);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        notification.setToUser(userList);
        user.getNotifications().add(notification);
        user.addRequest(requestSender);
        notificationRepository.save(notification);
        userRepository.save(user);
        log.info("Your request successfully sand");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ваш запрос успешно отправлен")
                .build();
    }

    @Transactional
    @Override
    public SimpleResponse acceptRequest(Long friendId,Request request) {
        User user = getAuthFromUser();
        User requestSender = userRepository.findById(friendId).orElseThrow(
                () -> {
                    log.info("User with friendId: " + friendId + " not found");
                    return new NotFoundException(String.format("Пользователь с идентификатором: %s не найден!", friendId));
                });
        if (!user.getRequest().contains(requestSender)) {
            log.info("Request not found");
            throw new NotFoundException("Запрос не найден");
        }
        if(request.equals(Request.REJECT_REQUEST)){
            requestSender.getRequest().remove(user);
            user.getRequest().remove(requestSender);
            log.info("Rejected request");
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Запрос отклонен")
                    .build();
        }
        if(request.equals(Request.ACCEPT_REQUEST)) {

            Notification notification = new Notification();
            notification.setLocalDate(LocalDate.now());
            notification.setRequest(Request.ACCEPT_REQUEST);
            notification.setSeen(false);
            List<User> fromUser = new ArrayList<>();
            fromUser.add(user);
            notification.setFromUser(fromUser);
            List<User> userList = new ArrayList<>();
            userList.add(user);
            notification.setToUser(userList);
            user.getNotifications().add(notification);
            user.addFriends(requestSender);
            requestSender.getRequest().remove(user);
            user.getRequest().remove(requestSender);
            requestSender.addFriends(user);
            notificationRepository.save(notification);
            userRepository.save(user);
            userRepository.save(requestSender);
            log.info("accepted Request");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Запрос принят")
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse deleteUserFromFriend(Long friendId) {
        User user = getAuthFromUser();
        User friend1 = userRepository.findById(friendId).orElseThrow(
                () -> {
                    log.info("User with friendId: " + friendId + " not found");
                    return new NotFoundException(String.format("Пользователь с идентификатором: %s не найден!", friendId));
                });
        List<Long> friends = user.getFriends();
        if (friends.contains(friend1.getId())) {
            friends.remove(friend1.getId());
            friend1.getFriends().remove(user.getId());
            log.info("Пользователь успешно удален из списка друзей");
        } else {
            log.info("User not found");
            throw new NotFoundException("Пользователь не найден");
        }
        userRepository.save(user);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пользователь успешно удален из списка друзей")
                .build();
    }

    @Override
    public List<MyFriendsResponse> getAllUserFriends(Long userId) {
        return userJDBCTemplate.getAllUserFriends(userId);
    }
}