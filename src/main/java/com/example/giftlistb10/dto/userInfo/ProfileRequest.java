package com.example.giftlistb10.dto.userInfo;

import com.example.giftlistb10.enums.ClothingSize;
import com.example.giftlistb10.enums.Country;
import com.example.giftlistb10.enums.ShoeSize;
import com.example.giftlistb10.validations.PhoneNumberValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ProfileRequest {
    @NotBlank(message = "Имя не должно быть пустым!")
    private String firstName;
    @NotBlank(message = "Фамилия не должна быть пустой!")
    private String lastName;
    private Country country;
    private LocalDate dateOfBirth;
    @PhoneNumberValidation
    private String phoneNumber;
    private String image;
    private ClothingSize clothingSize;
    private ShoeSize shoeSize;
    private String hobby;
    private String important;
    private String linkFacebook;
    private String vkontakte;
    private String instagram;
    private String telegram;
}