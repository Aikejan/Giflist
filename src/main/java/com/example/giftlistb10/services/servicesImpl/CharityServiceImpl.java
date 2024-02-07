package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.dto.charity.CharityRequest;
import com.example.giftlistb10.dto.charity.CharityResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.entities.Charity;
import com.example.giftlistb10.entities.Notification;
import com.example.giftlistb10.entities.User;
import com.example.giftlistb10.enums.*;
import com.example.giftlistb10.exceptions.BadRequestException;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.CharityRepository;
import com.example.giftlistb10.repositories.NotificationRepository;
import com.example.giftlistb10.repositories.UserRepository;
import com.example.giftlistb10.repositories.jdbcTemplate.CharityJDBCTemplate;
import com.example.giftlistb10.services.CharityService;
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
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class CharityServiceImpl implements CharityService {

    private final CharityJDBCTemplate charityJDBC;
    private final CharityRepository charityRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public User getPrinciple() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.getUserByEmail(email).orElseThrow(
                () -> new NotFoundException("Не найден!"));
    }

    public SimpleResponse saveCharity(CharityRequest charityRequest) {
        User user = getPrinciple();
        Charity charity = new Charity();
        charity.setNameCharity(charityRequest.getNameCharity());
        charity.setCategory(charityRequest.getCategory());
        charity.setSubCategory(charityRequest.getSubcategory());
        charity.setImage(charityRequest.getImage());
        charity.setCreatedAt(LocalDate.now());
        charity.setStatus(Status.PENDING);
        charity.setCondition(charityRequest.getCondition());
        charity.setDescription(charityRequest.getDescription());
        charity.setOwner(user);
        charity.setIsBlock(false);
        user.getCharities().add(charity);
        charity.setOwner(user);

        Notification notification = new Notification();
        notification.setLocalDate(LocalDate.now());
        notification.setStatus(Status.ADDED_CHARITY);
        notification.setSeen(false);
        List<User> fromUser = new ArrayList<>();
        fromUser.add(user);
        notification.setFromUser(fromUser);
        List<User> friendsToNotify = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            userRepository.findById(friendId).ifPresent(friendsToNotify::add);
        }
        if (!friendsToNotify.isEmpty()) {
            charity.setNotification(notification);
            charity.getNotification().setToUser(friendsToNotify);
        }
        charityRepository.save(charity);
        notificationRepository.save(notification);
        log.info("Charity successfully saved in database");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Благотворительность успешно сохранена")
                .build();
    }

    @Override
    public SimpleResponse deleteCharityById(Long charityId) {
        User user = getPrinciple();
        if (user.getRole().equals(Role.USER)) {
            if (!user.getCharities().contains(charityRepository.findById(charityId).orElseThrow(() -> new NotFoundException("Не найден!")))) {
                log.error("You can't delete other charities! ");
                throw new BadRequestException("Вы не можете удалять другие благотворительности");
            }
            charityRepository.deleteById(charityId);
        } else if(user.getRole().equals(Role.ADMIN)) {
            charityRepository.deleteById(charityId);

        }
        log.info("Charity with id: {} successfully deleted ", charityId);
        return new SimpleResponse(HttpStatus.OK, "Благотворительность успешно удалена");
    }


    @Override
    public SimpleResponse updateCharity(Long charityId, CharityRequest updatedCharityRequest) {
        Charity charity = charityRepository.findById(charityId).orElseThrow(
                () -> new NotFoundException("Благотворительность с ID " + charityId + " не найдена"));
        charity.setNameCharity(updatedCharityRequest.getNameCharity());
        charity.setCategory(updatedCharityRequest.getCategory());
        charity.setSubCategory(updatedCharityRequest.getSubcategory());
        charity.setDescription(updatedCharityRequest.getDescription());
        charity.setImage(updatedCharityRequest.getImage());
        charity.setCondition(updatedCharityRequest.getCondition());
        charityRepository.save(charity);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Благотворительность успешно обновлена")
                .build();
    }

    @Override
    public Optional<CharityResponse> getCharityById(Long charityId) {
       return charityJDBC.getCharityById(charityId);
    }

    @Override
    public List<CharityResponse> searchCharity(String value, Condition condition, Category category, SubCategory subCategory, Country country) {
        return charityJDBC.searchCharity(value,condition,category,subCategory,country);
    }

    @Override
    public List<CharityResponse> getAllCharities() {
        return charityJDBC.getAllCharities();
    }
    @Override
    public List<CharityResponse> getAllMyCharities(Long userId) {
        return charityJDBC.getAllMyCharities(userId);
    }
    @Override
    public List<CharityResponse> getAllFriendsCharities(Long userId) {
        return charityJDBC.getAllFriendsCharities(userId);
    }

    @Override
    public SimpleResponse blockCharityFromUser(Long charityId,boolean blockCharity) {
        Charity charity = charityRepository.findById(charityId).orElseThrow(
                () -> {
                    log.info("Charity with id: " + charityId + " not found!");
                    return new NotFoundException(String.format("Благотворительность с идентификатором: %s не найден!", charityId));
                });

        if (blockCharity) {
            charity.setIsBlock(true);
            charityRepository.save(charity);
            log.info("Charity blocked");
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Благотворительность заблокирован!")
                    .build();
        } else {
            charity.setIsBlock(false);
            charityRepository.save(charity);
            log.info("Charity unblocked");
        } return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Благотворительность разблокирован")
                .build();
    }
}