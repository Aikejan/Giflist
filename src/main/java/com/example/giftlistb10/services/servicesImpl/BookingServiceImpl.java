package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.dto.booking.CharityBookingResponse;
import com.example.giftlistb10.dto.booking.WishBookingResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.entities.*;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.exceptions.*;
import com.example.giftlistb10.repositories.*;
import com.example.giftlistb10.repositories.jdbcTemplate.WishJdbcTemplate;
import com.example.giftlistb10.services.BookingService;
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
public class BookingServiceImpl implements BookingService {

    private final CharityRepository charityRepository;
    private final UserRepository userRepository;
    private final WishesRepository wishesRepository;
    private final WishJdbcTemplate wishJdbcTemplate;
    private final NotificationRepository notificationRepository;

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
    public SimpleResponse bookingCharity(Long charityId, boolean reserveAnonymous) {
        User user = getAuthFromUser();

        Charity charity = charityRepository.findById(charityId).orElseThrow(() -> {
            log.info("Charity with id: " + charityId + " not found");
            return new NotFoundException("Благотворительность с идентификатором: " + charityId + " не найдена");
        });
        if (charity.getStatus().equals(Status.PENDING)) {
            if (reserveAnonymous) {
                charity.setStatus(Status.RESERVED_ANONYMOUSLY);
                charity.setReservoir(user);

                Notification notification = new Notification();
                List<User> userList = new ArrayList<>();
                userList.add(charity.getOwner());
                charity.getNotification().setToUser(userList);
                List<User> fromUser = new ArrayList<>();
                fromUser.add(user);
                notification.setFromUser(fromUser);
                notification.setSeen(false);
                notification.setLocalDate(LocalDate.now());
                notification.setStatus(Status.BOOKED_CHARITY_ANONYMOUSLY);
                charity.setNotification(notification);
                notificationRepository.save(notification);
                userRepository.save(user);
            } else {
                charity.setStatus(Status.RESERVED);
                charity.setReservoir(user);

                Notification notification = new Notification();
                List<User> userList = new ArrayList<>();
                userList.add(charity.getOwner());
                charity.getNotification().setToUser(userList);
                List<User> fromUser = new ArrayList<>();
                fromUser.add(user);
                notification.setFromUser(fromUser);
                notification.setSeen(false);
                notification.setLocalDate(LocalDate.now());
                notification.setStatus(Status.BOOKED_CHARITY);
                charity.setNotification(notification);
                notificationRepository.save(notification);
                userRepository.save(user);
            }
        } else {
            throw new AlreadyExistException("Благотворительность уже забронирована");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Благотворительность успешно забронировано")
                .build();
    }

    @Override
    public SimpleResponse bookingWish(Long wishId, boolean reserveAnonymous) {
        User user = getAuthFromUser();

        Wish wish = wishesRepository.findById(wishId).orElseThrow(() -> {
            log.info("Wish with id:" + wishId + " not found");
            return new NotFoundException("Пожелание с идентификатором: " + wishId + " не найдено");
        });
        if (wish.getStatusWish().equals(Status.PENDING)) {
            if (reserveAnonymous) {
                wish.setStatusWish(Status.RESERVED_ANONYMOUSLY);
                if (wish.getReservoir() == null) {
                    wish.setReservoir(user);

                    Notification notification = new Notification();
                    notification.setLocalDate(LocalDate.now());
                    notification.setStatus(Status.BOOKED_WISH_ANONYMOUSLY);
                    List<User> userList = new ArrayList<>();
                    userList.add(wish.getOwner());
                    notification.setToUser(userList);
                    List<User> fromUser = new ArrayList<>();
                    fromUser.add(user);
                    notification.setFromUser(fromUser);
                    notification.setSeen(false);
                    wish.setNotification(notification);
                    notificationRepository.save(notification);
                    wishesRepository.save(wish);
                    userRepository.save(user);
                } else throw new AlreadyExistException("");
            } else {
                wish.setStatusWish(Status.RESERVED);
                if (wish.getReservoir() == null) {
                    wish.setReservoir(user);

                    Notification notification = new Notification();
                    notification.setLocalDate(LocalDate.now());
                    notification.setStatus(Status.BOOKED_WISH);
                    List<User> userList = new ArrayList<>();
                    userList.add(wish.getOwner());
                    notification.setToUser(userList);
                    List<User> fromUser = new ArrayList<>();
                    fromUser.add(user);
                    notification.setFromUser(fromUser);
                    notification.setSeen(false);
                    wish.setNotification(notification);
                    notificationRepository.save(notification);
                    wishesRepository.save(wish);
                    userRepository.save(user);
                } else throw new AlreadyExistException("Пожелание уже забронирована");
            }
        } else {
            log.info("Wish is already booked");
            throw new AlreadyExistException("Пожелание уже забронировано");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пожелание успешно забронировано")
                .build();
    }

    @Override
    public SimpleResponse unBookingCharity(Long charityId) {
        User user = getAuthFromUser();

        Charity charity = charityRepository.findById(charityId).orElseThrow(() -> {
            log.info("Charity with id: " + charityId + " not found");
            return new NotFoundException("Благотворительность с идентификатором: " + charityId + " не найдена");
        });

        if (charity.getReservoir().equals(user)) {
            if (charity.getStatus().equals(Status.RESERVED) || charity.getStatus().equals(Status.RESERVED_ANONYMOUSLY)) {
                Notification notification = new Notification();
                notification.setLocalDate(LocalDate.now());
                notification.setStatus(Status.UNBOOKED);
                List<User> userList = new ArrayList<>();
                userList.add(charity.getOwner());
                notification.setToUser(userList);
                List<User> fromUser = new ArrayList<>();
                fromUser.add(user);
                notification.setFromUser(fromUser);
                notification.setSeen(false);
                notificationRepository.save(notification);
                charity.setStatus(Status.PENDING);
                charity.setNotification(notification);
                charity.setReservoir(null);
                charityRepository.save(charity);
                userRepository.save(user);
            } else {
                log.info("Charity stay on waiting");
                throw new BadCredentialException("Благотворительность находится в ожидании");
            }
        } else {
            throw new BadCredentialException("Вы не можете отменить бронирование благотворительности");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Благотворительность успешно разбронирована")
                .build();
    }

    @Override
    public SimpleResponse unBookingWish(Long wishId) {
        User user = getAuthFromUser();

        Wish wish = wishesRepository.findById(wishId).orElseThrow(() -> {
            log.info("Wish with id:" + wishId + " not found");
            return new NotFoundException("Пожелание с идентификатором: " + wishId + " не найдено");
        });
        if (wish.getReservoir().equals(user)) {
            if (wish.getStatusWish().equals(Status.RESERVED) || wish.getStatusWish().equals(Status.RESERVED_ANONYMOUSLY)) {
                Notification notification = new Notification();
                notification.setLocalDate(LocalDate.now());
                notification.setStatus(Status.UNBOOKED);
                List<User> userList = new ArrayList<>();
                userList.add(wish.getOwner());
                notification.setToUser(userList);
                List<User> fromUser = new ArrayList<>();
                fromUser.add(user);
                notification.setFromUser(fromUser);
                notification.setSeen(false);
                notificationRepository.save(notification);
                wish.setReservoir(null);
                wish.setStatusWish(Status.PENDING);
                wish.setNotification(notification);
                wishesRepository.save(wish);
                userRepository.save(user);

            } else {
                log.info("Wish is not reserved");
                throw new BadCredentialException("Пожелание не находится в забронированном состоянии");
            }
        } else {
            throw new BadCredentialException("Вы не можете отменить бронирование пожелание");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пожелание успешно разбронирована")
                .build();
    }

    @Override
    public List<WishBookingResponse> getAllReservedWish() {
        User user = getAuthFromUser();
        return wishJdbcTemplate.getBookingWishes(user.getEmail());
    }

    @Override
    public List<CharityBookingResponse> getAllReservedCharity() {
        User user = getAuthFromUser();
        return charityRepository.getAllReservedCharity(user.getEmail());
    }
}