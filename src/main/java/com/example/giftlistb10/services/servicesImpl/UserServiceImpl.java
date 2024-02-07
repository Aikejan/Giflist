package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.config.senderConfig.EmailSenderConfig;
import com.example.giftlistb10.dto.userInfo.UserChangePasswordRequest;
import com.example.giftlistb10.dto.userInfo.ProfileRequest;
import com.example.giftlistb10.dto.userInfo.UserResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.entities.*;
import com.example.giftlistb10.exceptions.BadCredentialException;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.*;
import com.example.giftlistb10.repositories.jdbcTemplate.UserJDBCTemplate;
import com.example.giftlistb10.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserJDBCTemplate userJDBCTemplate;
    private final PasswordEncoder passwordEncoder;
    private final MailingRepository mailingRepository;
    private final ComplainRepository complainRepository;
    private final CharityRepository charityRepository;
    private final NotificationRepository notificationRepository;
    private final EmailSenderConfig emailSenderConfig;

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
    public SimpleResponse fillingProfile(ProfileRequest profileRequest) {
        User user = getAuthFromUser();

        user.getUserInfo().setCountry(profileRequest.getCountry());
        user.getUserInfo().setDateOfBirth(profileRequest.getDateOfBirth());
        user.getUserInfo().setPhoneNumber(profileRequest.getPhoneNumber());
        user.getUserInfo().setImage(profileRequest.getImage());
        user.getUserInfo().setClothingSize(profileRequest.getClothingSize());
        user.getUserInfo().setShoeSize(profileRequest.getShoeSize());
        user.getUserInfo().setHobby(profileRequest.getHobby());
        user.getUserInfo().setImportant(profileRequest.getImportant());
        user.getUserInfo().setLinkFaceBook(profileRequest.getLinkFacebook());
        user.getUserInfo().setLinkVkontakte(profileRequest.getVkontakte());
        user.getUserInfo().setLinkInstagram(profileRequest.getInstagram());
        user.getUserInfo().setLinkTelegram(profileRequest.getTelegram());
        user.setFirstName(profileRequest.getFirstName());
        user.setLastName(profileRequest.getLastName());
        userRepository.save(user);
        log.info("Profile fulled");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Профиль заполнен")
                .build();
    }

    @Override
    public UserResponse getOwnProfile() {
        User user = getAuthFromUser();
        return userJDBCTemplate.getOwnProfile(user.getEmail());
    }

    @Override
    public UserResponse getProfileByUserId(Long userId) {
        return userJDBCTemplate.getProfileByUserId(userId);
    }

    @Override
    public SimpleResponse changePassword(UserChangePasswordRequest changePassword) {
        User user = getAuthFromUser();
        if (passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            if (changePassword.getNewPassword().equals(changePassword.getRepeatPassword())) {
                String encodedPassword = passwordEncoder.encode(changePassword.getNewPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Пароль изменен")
                        .build();
            } else {
                throw new BadCredentialException("Новый пароль и повторенный пароль не совпадают");
            }
        } else {
            throw new BadCredentialException("Старый пароль неверен");
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userJDBCTemplate.getAllUsers();
    }

    public SimpleResponse block(Long userId, boolean block) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    log.info("User with id: " + userId + " not found!");
                    return new NotFoundException(String.format("Пользователь с идентификатором: %s не найден!", userId));
                });

        if (block) {
            user.setBlock(true);
            userRepository.save(user);
            log.info("Account blocked");
            Context context = new Context();
            sendToUserBlock(user.getEmail(), "Аккаунт заблокирован!", context);

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Аккаунт заблокирован!")
                    .build();
        } else {
            user.setBlock(false);
            userRepository.save(user);
            log.info("Account unblocked");
            Context context = new Context();
            sendToUserUnblock(user.getEmail(), "Аккаунт разблокирован!", context);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Аккаунт разблокирован")
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    log.info("User with id: " + userId + " not found!");
                    return new NotFoundException(String.format("Пользователь с идентификатором: %s не найден!", userId));
                });
        for (Charity charity : charityRepository.getAllByReservoirId(userId)) {
            charity.setReservoir(null);
            charityRepository.save(charity);
        }
        List<Notification> notifications = notificationRepository.findAll();
        for (Notification notify : notifications) {
            notify.getFromUser().remove(user);
            notify.getToUser().remove(user);
            notificationRepository.save(notify);
        }
        List<Complain> complains = complainRepository.findAll();
        for (Complain complain : complains) {
            complain.getFromUser().remove(user);
            complainRepository.save(complain);
        }
        List<MailingList> mailingList = mailingRepository.findAll();
        for (MailingList mailingList1 : mailingList) {
            mailingList1.getUsers().remove(user);
            mailingRepository.save(mailingList1);
            List<User> users = userRepository.findAll();
            for (User user1 : users) {
                user1.getMailingList().remove(mailingList1);
                user1.getRequest().remove(user);
                userRepository.save(user1);
            }
        }

        userRepository.save(user);
        userRepository.delete(user);
        log.info("User with id:" + userId + " deleted");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Пользователь с идентификатором: %s успешно удален", userId))
                .build();
    }

    @Override
    public List<UserResponse> searchUserByName(String userName) {
        return userJDBCTemplate.searchUserByName(userName);
    }

    private void sendToUserBlock(String email, String subject, Context context) {
        emailSenderConfig.sendEmailWithHTMLTemplate(email, "giftlistj10@gmail.com", subject, "blockUser", context);
    }

    private void sendToUserUnblock(String email, String subject, Context context) {
        emailSenderConfig.sendEmailWithHTMLTemplate(email, "giftlistj10@gmail.com", subject, "unBlockUser", context);
    }
}