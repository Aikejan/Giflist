package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.dto.charity.CharityResponse;
import com.example.giftlistb10.dto.feed.UserFeedResponse;
import com.example.giftlistb10.dto.holiday.HolidayResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.wish.WishesResponse;
import com.example.giftlistb10.entities.Notification;
import com.example.giftlistb10.entities.User;
import com.example.giftlistb10.entities.Wish;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.UserRepository;
import com.example.giftlistb10.repositories.WishesRepository;
import com.example.giftlistb10.repositories.jdbcTemplate.CharityJDBCTemplate;
import com.example.giftlistb10.repositories.jdbcTemplate.HolidayJDBCTemplate;
import com.example.giftlistb10.repositories.jdbcTemplate.WishJdbcTemplate;
import com.example.giftlistb10.services.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FeedServiceImpl implements FeedService {

    private final WishesRepository wishesRepository;
    private final UserRepository userRepository;
    private final WishJdbcTemplate wishJdbcTemplate;
    private final CharityJDBCTemplate charityJDBCTemplate;
    private final HolidayJDBCTemplate holidayJDBCTemplate;

    @Override
    public SimpleResponse copyWishToUser(Long wishId, Long userId) {
        User currentUser = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с идентификатором : %s не найден!".formatted(userId)));

        Wish wishToCopy = wishesRepository.findById(wishId).orElseThrow(() ->
                new NotFoundException("Пожелание с идентификатором : %s не найден!".formatted(wishId)));

        Wish copiedWish = new Wish();
        copiedWish.setNameWish(wishToCopy.getNameWish());
        copiedWish.setImage(wishToCopy.getImage());
        copiedWish.setLinkToGift(wishToCopy.getLinkToGift());
        copiedWish.setStatusWish(Status.PENDING);
        copiedWish.setDescription(wishToCopy.getDescription());
        copiedWish.setHoliday(wishToCopy.getHoliday());
        wishToCopy.getIsAllReadyInWishList().add(userId);
        currentUser.getWishes().add(copiedWish);

        Notification notification = new Notification();
        notification.setLocalDate(LocalDate.now());
        notification.setStatus(Status.ADDED_WISH);
        notification.setSeen(false);
        List<User> fromUser = new ArrayList<>();
        fromUser.add(currentUser);
        notification.setFromUser(fromUser);
        List<User> friendsToNotify = new ArrayList<>();
        for (Long friendId : currentUser.getFriends()) {
            userRepository.findById(friendId).ifPresent(friendsToNotify::add);
        }
        if (!friendsToNotify.isEmpty()) {
            copiedWish.setNotification(notification);
            copiedWish.getNotification().setToUser(friendsToNotify);
        }
        wishesRepository.save(copiedWish);
        copiedWish.setOwner(currentUser);
        userRepository.save(currentUser);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пожелание с идентификатором : %s теперь в вашем списке пожеланий.".formatted(wishId))
                .build();
    }

    @Override
    public UserFeedResponse feedResponse(Long userId) {
        UserFeedResponse userWishResponse = new UserFeedResponse();
        List<WishesResponse> wishResponses = wishJdbcTemplate.getAllWishesFriends(userId);
        List<CharityResponse> charityResponses = charityJDBCTemplate.getAllFriendsCharities(userId);
        List<HolidayResponse> holidayResponses = holidayJDBCTemplate.getAllHolidaysFriendsById(userId);
        userWishResponse.setWishesResponses(wishResponses);
        userWishResponse.setCharityResponses(charityResponses);
        userWishResponse.setHolidayResponses(holidayResponses);
        return userWishResponse;
    }
}