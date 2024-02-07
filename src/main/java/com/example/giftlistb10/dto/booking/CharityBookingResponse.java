package com.example.giftlistb10.dto.booking;

import com.example.giftlistb10.enums.Condition;
import com.example.giftlistb10.enums.Status;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class CharityBookingResponse {
    private Long id;
    private String charityName;
    private Condition condition;
    private LocalDate dateOfHoliday;
    private String image;
    private Status wishStatus;
    private Long ownerId;
    private String ownerFullName;
    private String ownerImage;
    private Long reservoirId;

    public CharityBookingResponse(Long id, String charityName, Condition condition, LocalDate dateOfHoliday, String image, Status wishStatus, Long reservedId, String ownerFullName, String ownerImage,Long reservoirId) {
        this.id = id;
        this.charityName = charityName;
        this.condition = condition;
        this.dateOfHoliday = dateOfHoliday;
        this.image = image;
        this.wishStatus = wishStatus;
        this.ownerId = reservedId;
        this.ownerFullName = ownerFullName;
        this.ownerImage = ownerImage;
        this.reservoirId = reservoirId;
    }
}