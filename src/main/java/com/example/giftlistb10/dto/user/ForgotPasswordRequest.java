package com.example.giftlistb10.dto.user;

import com.example.giftlistb10.validations.PasswordValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotNull
    @NotBlank
    @PasswordValidation
    private String newPassword;
    @NotNull
    @NotBlank
    @PasswordValidation
    private String verifyPassword;
}