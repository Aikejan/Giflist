package com.example.giftlistb10.dto.wish;

import com.example.giftlistb10.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class WishesResponse {
    private Long wishId;
    private String wishName;
    private String wishImage;
    private Status wishStatus;
    private String description;
    private String holidayName;
    private LocalDate dateOfHoliday;
    private Long ownerId;
    private String fullName;
    private boolean isBlock;
    private String reservoirFullName;
    private String userImage;
    private Long reservoirId;
    private String reservoirImage;
    private boolean isAllReadyInWishList;
    private String linkToWish;

    public WishesResponse(Long wishId, String wishName, String wishImage, Status wishStatus,
                          String holidayName, LocalDate dateOfHoliday,String reservoirImage, String linkToWish) {
        this.wishId = wishId;
        this.wishName = wishName;
        this.wishImage = wishImage;
        this.wishStatus = wishStatus;
        this.holidayName = holidayName;
        this.dateOfHoliday = dateOfHoliday;
        this.reservoirImage = reservoirImage;
        this.linkToWish = linkToWish;
    }

    public WishesResponse(Long wishId, String wishName, String wishImage, Status wishStatus,
                          String holidayName, LocalDate dateOfHoliday, String linkToWish,boolean isBlock) {
        this.wishId = wishId;
        this.wishName = wishName;
        this.wishImage = wishImage;
        this.wishStatus = wishStatus;
        this.holidayName = holidayName;
        this.dateOfHoliday = dateOfHoliday;
        this.linkToWish = linkToWish;
        this.isBlock=isBlock;
    }

    public WishesResponse(Long wishId, String wishName, String wishImage, Status wishStatus,String description ,String holidayName,
                          LocalDate dateOfHoliday, Long ownerId, String fullName,boolean isBlock, String userImage,
                          Long reservoirId, String reservoirImage, String linkToWish) {
        this.wishId = wishId;
        this.wishName = wishName;
        this.wishImage = wishImage;
        this.wishStatus = wishStatus;
        this.description = description;
        this.holidayName = holidayName;
        this.dateOfHoliday = dateOfHoliday;
        this.ownerId = ownerId;
        this.fullName = fullName;
        this.isBlock = isBlock;
        this.userImage = userImage;
        this.reservoirId = reservoirId;
        this.reservoirImage = reservoirImage;
        this.linkToWish = linkToWish;
    }

    public WishesResponse(Long wishId, String wishName, String wishImage, Status wishStatus, String holidayName,
                          LocalDate dateOfHoliday, Long ownerId, String fullName,String reservoirFullName, String userImage, Long reservoirId,
                          String reservoirImage,boolean isAllReadyInWishList, String linkToWish) {
        this.wishId = wishId;
        this.wishName = wishName;
        this.wishImage = wishImage;
        this.wishStatus = wishStatus;
        this.holidayName = holidayName;
        this.dateOfHoliday = dateOfHoliday;
        this.ownerId = ownerId;
        this.fullName = fullName;
        this.reservoirFullName=reservoirFullName;
        this.userImage = userImage;
        this.reservoirId = reservoirId;
        this.reservoirImage = reservoirImage;
        this.isAllReadyInWishList = isAllReadyInWishList;
        this.linkToWish = linkToWish;
    }
}