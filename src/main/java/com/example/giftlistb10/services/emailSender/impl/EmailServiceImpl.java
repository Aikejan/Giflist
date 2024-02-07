package com.example.giftlistb10.services.emailSender.impl;

import com.example.giftlistb10.config.JwtService;
import com.example.giftlistb10.config.senderConfig.EmailSenderConfig;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.entities.User;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.UserRepository;
import com.example.giftlistb10.services.emailSender.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final EmailSenderConfig emailSenderConfig;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    @Override
    public SimpleResponse sendHtmlMessage(String email, String linkToChangePassword) {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> {
            log.info("User is not found !!!");
            return new NotFoundException("Пользователь не найден !!!");
        });
        String token = jwtService.generateToken(user);
        String subject = "reset";
        String from = "giftlistj10@gmail.com";
        String templateName = "reset_password";
        Context context = new Context();
        context.setVariable("userName", user.getFirstName() + "!");
        context.setVariable("link", linkToChangePassword);
        emailSenderConfig.sendEmailWithHTMLTemplate(user.getEmail(), from, subject, templateName, context);
        log.info("Method Send Email With HTML is working !!!");
        return SimpleResponse.builder()
                .message(token)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}