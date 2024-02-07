package com.example.giftlistb10.dto.user;

import com.example.giftlistb10.validations.EmailValidation;
import com.example.giftlistb10.validations.PasswordValidation;

public record SignInRequest(
        @EmailValidation
        String email,
        @PasswordValidation
        String password
) {
}