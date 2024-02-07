package com.example.giftlistb10.dto.userInfo;

import com.example.giftlistb10.validations.PasswordValidation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordRequest {
    @PasswordValidation
    private String oldPassword;
    @PasswordValidation
    private String newPassword;
    @PasswordValidation
    private String repeatPassword;
}
