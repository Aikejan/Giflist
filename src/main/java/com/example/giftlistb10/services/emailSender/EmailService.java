package com.example.giftlistb10.services.emailSender;

import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.validations.EmailValidation;

import java.time.LocalDate;

public interface EmailService {
    SimpleResponse sendHtmlMessage(@EmailValidation String email, String linkToChangePassword);

}