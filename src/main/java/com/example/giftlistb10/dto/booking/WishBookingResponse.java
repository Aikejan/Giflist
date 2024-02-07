package com.example.giftlistb10.dto.booking;

import com.example.giftlistb10.enums.Status;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class WishBookingResponse {
    private Long id;
    private String nameWish;
    private String nameHoliday;
    private LocalDate dateOfHoliday;
    private String image;
    private Status wishStatus;
    private Long ownerId;
    private String ownerFullName;
    private String ownerImage;
    private Long reservoirId;
    private boolean isAlreadyInWishList;

    public WishBookingResponse(Long id, String nameWish, String nameHoliday, LocalDate dateOfHoliday,
                               String image, Status wishStatus, Long reservedId, String ownerFullName, String ownerImage,Long reservoirId,boolean isAlreadyInWishList) {
        this.id = id;
        this.nameWish = nameWish;
        this.nameHoliday = nameHoliday;
        this.dateOfHoliday = dateOfHoliday;
        this.image = image;
        this.wishStatus = wishStatus;
        this.ownerId = reservedId;
        this.ownerFullName = ownerFullName;
        this.ownerImage = ownerImage;
        this.reservoirId = reservoirId;
        this.isAlreadyInWishList = isAlreadyInWishList;
    }
}