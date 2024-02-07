package com.example.giftlistb10.config;

import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MailConfig {

    @Value("${spring.gmail.username}")
    private String gmailUsername;

    @Value("${spring.gmail.password}")
    private String gmailPassword;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(gmailUsername);
        mailSender.setPassword(gmailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Async
    public void send(String to, String htmlMessage, String subject) {
        MimeMessage mimeMessage = javaMailSender().createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setSubject("[Gift-List] " + subject);
            helper.setFrom("giftlistj10@gmail.com");
            helper.setTo(to);
            helper.setText(htmlMessage, true);
            javaMailSender().send(mimeMessage);
            log.info("Mailing sent success");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Mailing not sent");
        }
    }
}