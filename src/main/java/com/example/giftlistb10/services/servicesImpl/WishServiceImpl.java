package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.wish.WishesRequest;
import com.example.giftlistb10.dto.wish.WishesResponse;
import com.example.giftlistb10.entities.Holiday;
import com.example.giftlistb10.entities.Notification;
import com.example.giftlistb10.entities.User;
import com.example.giftlistb10.entities.Wish;
import com.example.giftlistb10.enums.Role;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.exceptions.BadRequestException;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.HolidayRepository;
import com.example.giftlistb10.repositories.NotificationRepository;
import com.example.giftlistb10.repositories.UserRepository;
import com.example.giftlistb10.repositories.WishesRepository;
import com.example.giftlistb10.repositories.jdbcTemplate.WishJdbcTemplate;
import com.example.giftlistb10.services.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WishServiceImpl implements WishService {

    private final WishesRepository wishesRepository;
    private final UserRepository userRepository;
    private final WishJdbcTemplate wishJdbcTemplate;
    private final NotificationRepository notificationRepository;
    private final HolidayRepository holidayRepository;

    public User getAuthPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new BadRequestException("Некорректная аутентификация");
        }

        String email = auth.getName();
        return userRepository.getUserByEmail(email).orElseThrow(
                () -> new NotFoundException(String.format("Пользователь с таким электронным адресом: %s не найден!", email)));
    }

    @Override
    public List<WishesResponse> getAllWishes() {
        return wishJdbcTemplate.getAllWishes();
    }

    @Override
    public WishesResponse getWishById(Long id) {
        return wishJdbcTemplate.getWishById(id).orElseThrow(() ->
                new NotFoundException("Пожелание с идентификатором: %s не найдено".formatted(id)));
    }

    @Override
    public SimpleResponse saveWish(Long holidayId, WishesRequest request) {
        User user = getAuthPrincipal();
        Holiday holiday = holidayRepository.findById(holidayId).orElseThrow(() ->
                new NotFoundException("Праздник с идентификатором : %s не найден"));
        Wish wish = new Wish();
        wish.setNameWish(request.getNameWish());
        wish.setImage(request.getImage());
        wish.setLinkToGift(request.getLinkToGift());
        wish.setDescription(request.getDescription());
        wish.setStatusWish(Status.PENDING);
        wish.setHoliday(holiday);
        wish.setDateOfHoliday(holiday.getDateOfHoliday());
        wish.setOwner(user);

        Notification notification = new Notification();
        notification.setLocalDate(LocalDate.now());
        notification.setStatus(Status.ADDED_WISH);
        notification.setSeen(false);
        List<User> fromUser = new ArrayList<>();
        fromUser.add(user);
        notification.setFromUser(fromUser);
        List<User> friendsToNotify = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            userRepository.findById(friendId).ifPresent(friendsToNotify::add);
        }
        if (!friendsToNotify.isEmpty()) {
            wish.setNotification(notification);
            wish.getNotification().setToUser(friendsToNotify);
        }
        wishesRepository.save(wish);
        notificationRepository.save(notification);
        log.info("Wish with name: %s is saved".formatted(request.getNameWish()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пожелание с именем: %s успешно сохранено".formatted(request.getNameWish()))
                .build();
    }

    @Override
    public SimpleResponse updateWish(Long id, Long holidayId, WishesRequest request) {
        User user = getAuthPrincipal();
        Wish wish = wishesRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Желание с идентификатором: %s не найдено".formatted(id)));
        Holiday holiday = holidayRepository.findById(holidayId).orElseThrow(() ->
                new NotFoundException("Праздник с идентификатором : %s не найден"));
        wish.setNameWish(request.getNameWish());
        wish.setImage(request.getImage());
        wish.setLinkToGift(request.getLinkToGift());
        wish.setHoliday(holiday);
        wish.setDescription(request.getDescription());
        wish.setStatusWish(request.getStatus());
        wish.setOwner(user);
        wishesRepository.save(wish);
        log.info("Wish with id: %s is updated".formatted(id));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пожелание с идентификатором: %s успешно обновлено".formatted(id))
                .build();
    }

    @Override
    public SimpleResponse deleteWish(Long id) {
        User user = getAuthPrincipal();
        if (user.getRole().equals(Role.USER)) {
            if (!user.getWishes().contains(wishesRepository.findById(id).orElseThrow(() -> new NotFoundException("Не найден!")))) {
                log.error("You can't delete other wishes! ");
                throw new BadRequestException("Вы не можете удалять другие желанию");
            }
            wishesRepository.deleteById(id);
        } else if (user.getRole().equals(Role.ADMIN)) {
            wishesRepository.deleteById(id);

        }
        log.info("Wish with id: {} successfully deleted ", id);
        return new SimpleResponse(HttpStatus.OK, "Желание успешно удалена");
    }

    @Override
    public List<WishesResponse> getAllWishesOfUser(Long userId) {
        List<WishesResponse> wishesResponses = wishJdbcTemplate.getAllMyWishes(userId);
        return wishesResponses;
    }

    @Override
    public List<WishesResponse> getAllWishesFromFriends(Long userId) {
        List<WishesResponse> wishesResponses = wishJdbcTemplate.getAllWishesFriends(userId);
        return wishesResponses;
    }

    @Override
    public List<WishesResponse> searchWishByName(String name) {
        return wishJdbcTemplate.searchWishByName(name);
    }

    @Override
    public SimpleResponse blockOrUnblock(Long wishId, boolean block) {
        Wish wish = wishesRepository.findById(wishId).orElseThrow(() ->
                new NotFoundException("Пожелание с идентификатором: %s не найден".formatted(wishId)));

        if (block) {
            wish.setBlock(true);
            wishesRepository.save(wish);
            log.info("Wish blocked");
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Пожелание заблокирован!")
                    .build();
        } else {
            wish.setBlock(false);
            wishesRepository.save(wish);
            log.info("Wish unblocked");
        } return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пожелание разблокирован")
                .build();
    }
}