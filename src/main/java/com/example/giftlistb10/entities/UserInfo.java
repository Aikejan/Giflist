package com.example.giftlistb10.entities;

import com.example.giftlistb10.enums.ClothingSize;
import com.example.giftlistb10.enums.Country;
import com.example.giftlistb10.enums.ShoeSize;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "userInfo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "userInfo_gen")
    @SequenceGenerator(name = "userInfo_gen",
            sequenceName = "userInfo_seq",
            allocationSize = 1,
            initialValue = 10)
    private Long id;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Country country;
    private String phoneNumber;
    private String linkTelegram;
    private String linkInstagram;
    private String linkVkontakte;
    private String linkFaceBook;
    @Enumerated(EnumType.STRING)
    private ClothingSize clothingSize;
    @Enumerated(EnumType.STRING)
    private ShoeSize shoeSize;
    private String hobby;
    private String image;
    private String important;
}