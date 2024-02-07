package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.dto.complaint.*;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.entities.*;
import com.example.giftlistb10.enums.Role;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.enums.StatusComplaint;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.*;
import com.example.giftlistb10.repositories.jdbcTemplate.WishJdbcTemplate;
import com.example.giftlistb10.services.ComplainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class ComplainServiceImpl implements ComplainService {
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;
    private final WishesRepository wishesRepository;
    private final ComplainRepository complainRepository;
    private final CharityRepository charityRepository;
    private final WishJdbcTemplate wishJdbcTemplate;

    @Override
    public List<ComplaintResponse> getCharitiesWithComplaints() {
        List<ComplaintResponse> charitiesWithComplaints = complainRepository.getCharitiesWithComplaints();
        for (ComplaintResponse charityResponse : charitiesWithComplaints) {
            Long charityId = charityResponse.getCharityId();
            List<ComplaintUserResponse> complaints = complainRepository.getComplaintsCharity(charityId);
            charityResponse.setComplaints(complaints);
        }
        return charitiesWithComplaints;
    }

    @Override
    public ComplaintResponse getCharityComplaintById(Long charityId) {
        ComplaintResponse charityComplaintGetById = complainRepository.getComplainByCharityId(charityId).orElseThrow(null);
        List<ComplaintUserResponse> getComplain = complainRepository.getComplaintsCharity(charityId);
        charityComplaintGetById.setComplaints(getComplain);
        return charityComplaintGetById;
    }

    @Override
    public List<ComplaintResponse> getAllWishesWithComplaints() {
        List<ComplaintResponse> allWishesWithComplaints = wishJdbcTemplate.getAllWishesComplain();
        for (ComplaintResponse wishResponse : allWishesWithComplaints) {
            Long wishId = wishResponse.getWishId();
            List<ComplaintUserResponse> complaints = complainRepository.getComplaintsWish(wishId);
            wishResponse.setComplaints(complaints);
        }
        return allWishesWithComplaints;
    }

    @Override
    public ComplaintResponse getWishComplaintById(Long wishId) {
        ComplaintResponse wishComplaintGetById = complainRepository.getComplainByWishId(wishId).orElseThrow(null);
        List<ComplaintUserResponse> getComplaint = complainRepository.getComplaintsWish(wishId);
        wishComplaintGetById.setComplaints(getComplaint);
        return wishComplaintGetById;
    }

    @Override
    public SimpleResponse sendComplaintToWish(Long wishId, StatusComplaint status, String text) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                    new NotFoundException("Пользователь с адресом электронной почты: %s не найден".formatted(email)));
            Wish wish = wishesRepository.findById(wishId).orElseThrow(() ->
                    new NotFoundException("Желание с идентификатором: %s не найдено".formatted(wishId)));

            Complain complain = new Complain();
            complain.setWish(wish);
            complain.setStatusComplaint(status);
            complain.setTextComplain(text);
            complain.setCreatedAt(LocalDate.now());
            List<User> fromUser = new ArrayList<>();
            fromUser.add(user);
            complain.setFromUser(fromUser);
            if (complain.getFromUser() == null) {
                complain.setFromUser(new ArrayList<>());
            }
            wish.getComplains().add(complain);
            Notification notification = new Notification();
            List<User> userList = userRepository.findAll();
            for (User admin:userList) {
                if(admin.getRole().equals(Role.ADMIN)){
                    wish.setNotification(notification);
                    notification.setStatus(Status.COMPLAINT_WISH);
                    List<User> users = new ArrayList<>();
                    users.add(user);
                    notification.setFromUser(users);
                    List<User> admin2 = new ArrayList<>();
                    admin2.add(admin);
                    notification.setToUser(admin2);
                    }
                }
            complain.setNotification(notification);
            complainRepository.save(complain);
            notificationRepository.save(notification);
            log.info("Complaint is saved");
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Отправлено! Спасибо, что сообщили нам об этом" + " " +
                            " Ваши отзывы помогают нам сделать сообщество GIFT LIST безопасной средой для всех")
                    .build();
        } catch (Exception e) {
            log.error("Ошибка при сохранении жалобы", e);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Произошла ошибка при отправьке жалобы. Пожалуйста, попробуйте еще раз.")
                .build();
    }

    @Override
    public SimpleResponse sendComplaintToCharity(Long charityId, StatusComplaint status, String text) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                    new NotFoundException("Пользователь с адресом электронной почты: %s не найден".formatted(email)));
            Charity charity = charityRepository.findById(charityId).orElseThrow(() ->
                    new NotFoundException("Благотварительность с идентификатором: %s не найден".formatted(charityId)));

            Complain complain = new Complain();
            complain.setCharity(charity);
            complain.setStatusComplaint(status);
            complain.setTextComplain(text);
            complain.setCreatedAt(LocalDate.now());

            if (complain.getFromUser() == null) {
                complain.setFromUser(new ArrayList<>());
            }
            complain.getFromUser().add(user);
            charity.getComplains().add(complain);

            Notification notification = new Notification();
            List<User> userList = userRepository.findAll();
            for (User admin:userList) {
                if(admin.getRole().equals(Role.ADMIN)){
                    charity.setNotification(notification);
                    notification.setStatus(Status.COMPLAINT_WISH);
                    List<User> users = new ArrayList<>();
                    users.add(user);
                    notification.setFromUser(users);
                    List<User> admin2 = new ArrayList<>();
                    admin2.add(admin);
                    notification.setToUser(admin2);
                }
            }
            complain.setNotification(notification);
            complainRepository.save(complain);
            log.info("Charity is saved");
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Отправлено! Спасибо, что сообщили нам об этом" + " " +
                            " Ваши отзывы помогают нам сделать сообщество GIFT LIST безопасной средой для всех")
                    .build();

        } catch (Exception e) {
            log.error("Ошибка при сохранении жалобы", e);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Произошла ошибка при отправьке жалобы. Пожалуйста, попробуйте еще раз.")
                .build();
    }
}