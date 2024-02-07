package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.config.MailConfig;
import com.example.giftlistb10.config.senderConfig.EmailSenderConfig;
import com.example.giftlistb10.dto.mailingList.MailingResponse;
import com.example.giftlistb10.dto.mailingList.SendMailingRequest;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.entities.MailingList;
import com.example.giftlistb10.entities.User;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.MailingRepository;
import com.example.giftlistb10.repositories.UserRepository;
import com.example.giftlistb10.repositories.jdbcTemplate.MailingJdbcTemplate;
import com.example.giftlistb10.services.MailingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {

    private final UserRepository userRepository;
    private final MailingRepository mailingRepository;
    private final MailingJdbcTemplate mailingJdbcTemplate;
    private final MailConfig mailConfig;
    private final EmailSenderConfig senderConfig;

    @Override
    public SimpleResponse sendToAll(SendMailingRequest sendMailingRequest) throws IOException {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        List<User> users = userRepository.findUserByIsAgree();

        MailingList mailing = new MailingList();
        mailing.setImage(sendMailingRequest.getImage());
        mailing.setText(sendMailingRequest.getText());
        mailing.setNameMailing(sendMailingRequest.getNameMailing());
        mailing.setCreatedAt(LocalDate.parse(formattedDate));
        for (User user : users) {
            if (user.isAgree()) {
                sendHtmlMessage(user.getEmail(),mailing);
                mailing.setUsers(users);
            }
        }
        mailingRepository.save(mailing);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Рассылка отправлена")
                .build();
    }


    @Override
    public List<MailingResponse> getAllMailings() {
        return mailingJdbcTemplate.getAllMailings();
    }

    @Override
    public MailingResponse getById(Long mailingId) {
        return mailingJdbcTemplate.getByMailingId(mailingId).orElseThrow(() ->
                new NotFoundException("Рассылка с идентификатором : %s не найдена".formatted(mailingId)));
    }
    private SimpleResponse sendHtmlMessage(String email,MailingList mailing) {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> {
            log.info("User is not found !!!");
            return new NotFoundException("Пользователь не найден !!!");
        });
        String subject = "reset";
        String from = "giftlistj10@gmail.com";
        String templateName = "mailingMessage";
        Context context = new Context();
        context.setVariable("userName", user.getFirstName() + "!");
        context.setVariable("mailing", mailing);        senderConfig.sendEmailWithHTMLTemplate(user.getEmail(), from, subject, templateName, context);
        log.info("Method Send Email With HTML is working !!!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }
}