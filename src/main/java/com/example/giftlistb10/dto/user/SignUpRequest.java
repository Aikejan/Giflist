package com.example.giftlistb10.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.example.giftlistb10.validations.EmailValidation;
import com.example.giftlistb10.validations.PasswordValidation;

@Builder
@Getter
@Setter
public class SignUpRequest {
    @NotBlank(message = "Имя не должно быть пустым!")
    private String firstName;
    @NotBlank(message = "Фамилия не должна быть пустой!")
    private String lastName;
    @EmailValidation
    private String email;
    @PasswordValidation
    private String password;
    private Boolean isAgree;
}