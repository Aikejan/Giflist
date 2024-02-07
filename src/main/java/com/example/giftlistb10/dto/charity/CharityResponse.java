package com.example.giftlistb10.dto.charity;

import com.example.giftlistb10.enums.Category;
import com.example.giftlistb10.enums.Condition;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.enums.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  CharityResponse {
    private String fullName;
    private String reservoirFullName;
    private String userImage;
    private String nameCharity;
    private Long charityId;
    private Boolean isBlock;
    private String description;
    private String ownerPhoneNumber;
    private String charityImage;
    private Category category;
    private SubCategory subCategory;
    private LocalDate createdAt;
    private Condition condition;
    private Status status;
    private String bookedUserImage;
    private Long charityReservoirId;
    private Long userId;

    public CharityResponse(String fullName, String userImage,String nameCharity, Long charityId,Boolean isBlock, String charityImage, LocalDate createdAt,
                           Condition condition, Status status, String bookedUserImage, Long charityReservoirId) {
        this.fullName = fullName;
        this.userImage = userImage;
        this.nameCharity = nameCharity;
        this.charityId = charityId;
        this.isBlock = isBlock;
        this.charityImage = charityImage;
        this.createdAt = createdAt;
        this.condition = condition;
        this.status = status;
        this.bookedUserImage = bookedUserImage;
        this.charityReservoirId = charityReservoirId;
    }

    public CharityResponse(String fullName, String userImage, String nameCharity, Long charityId,Boolean isBlock, String description,
                           String ownerPhoneNumber, String charityImage, Category category, SubCategory subCategory,
                           LocalDate createdAt, Condition condition, Status status, String bookedUserImage, Long charityReservoirId) {
        this.fullName = fullName;
        this.userImage = userImage;
        this.nameCharity = nameCharity;
        this.charityId = charityId;
        this.isBlock = isBlock;
        this.description = description;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.charityImage = charityImage;
        this.category = category;
        this.subCategory = subCategory;
        this.createdAt = createdAt;
        this.condition = condition;
        this.status = status;
        this.bookedUserImage = bookedUserImage;
        this.charityReservoirId = charityReservoirId;
    }

    public CharityResponse(String fullName,String reservoirFullName, String userImage, String nameCharity, Long charityId,Boolean isBlock, String charityImage, LocalDate createdAt, Condition condition, Status status, String bookedUserImage, Long charityReservoirId, Long userId) {
        this.fullName = fullName;
        this.reservoirFullName=reservoirFullName;
        this.userImage = userImage;
        this.nameCharity = nameCharity;
        this.charityId = charityId;
        this.isBlock=isBlock;
        this.charityImage = charityImage;
        this.createdAt = createdAt;
        this.condition = condition;
        this.status = status;
        this.bookedUserImage = bookedUserImage;
        this.charityReservoirId = charityReservoirId;
        this.userId = userId;
    }
}