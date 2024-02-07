package com.example.giftlistb10.dto.userInfo;

import com.example.giftlistb10.enums.ClothingSize;
import com.example.giftlistb10.enums.Country;
import com.example.giftlistb10.enums.ShoeSize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String fullName;
    private Country country;
    private LocalDate dateOfBirth;
    private String email;
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
    private int wishSum;
    private boolean isBlock;


    public UserResponse(Long userId, String fullName, String image, int wishSum,boolean isBlock) {
        this.userId = userId;
        this.fullName = fullName;
        this.image = image;
        this.wishSum = wishSum;
        this.isBlock = isBlock;
    }

    public UserResponse(Long userId, String fullName, Country country, LocalDate dateOfBirth, String email, String phoneNumber, String image, ClothingSize clothingSize,
                        ShoeSize shoeSize, String hobby, String important, String linkFacebook, String vkontakte, String instagram, String telegram,boolean isBlock) {
        this.userId = userId;
        this.fullName = fullName;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.clothingSize = clothingSize;
        this.shoeSize = shoeSize;
        this.hobby = hobby;
        this.important = important;
        this.linkFacebook = linkFacebook;
        this.vkontakte = vkontakte;
        this.instagram = instagram;
        this.telegram = telegram;
        this.isBlock = isBlock;
    }

    public UserResponse(Long userId, String fullName, String image) {
        this.userId = userId;
        this.fullName = fullName;
        this.image = image;
    }
}