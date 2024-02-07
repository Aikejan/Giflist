package com.example.giftlistb10.dto.complaint;

import com.example.giftlistb10.enums.Category;
import com.example.giftlistb10.enums.Condition;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.enums.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintResponse {

    private Long userId;
    private String fullName;
    private String ownerImage;
    private String phoneNumber;
    private Long charityId;
    private String charityImage;
    private String reservoirImage;
    private String description;
    private String charityName;
    private Category category;
    private SubCategory subCategory;
    private Condition condition;
    private String createdAt;
    private Status statusCharity;
    private Long wishId;
    private String nameWish;
    private String wishImage;
    private String nameHoliday;
    private LocalDate dateOfHoliday;
    private Status statusWish;
    private boolean isBlock;
    private List<ComplaintUserResponse> complaints;

    public ComplaintResponse(Long userId,String fullName,String ownerImage, String phoneNumber,
                             Long charityId,String charityImage, String reservoirImage, String description,
                             String charityName, Category category, SubCategory subCategory,
                             Condition condition, String createdAt, Status status,boolean isBlock) {
        this.userId = userId;
        this.fullName = fullName;
        this.ownerImage = ownerImage;
        this.phoneNumber = phoneNumber;
        this.charityId=charityId;
        this.charityImage = charityImage;
        this.reservoirImage = reservoirImage;
        this.description = description;
        this.charityName = charityName;
        this.category = category;
        this.subCategory = subCategory;
        this.condition = condition;
        this.createdAt = createdAt;
        this.statusCharity = status;
        this.isBlock = isBlock;
    }

    public ComplaintResponse(Long userId,String fullName, String userImage,String phoneNumber,Long wishId,
                             String nameWish, String wishImage, String nameHoliday, LocalDate dateOfHoliday, Status statusWish,boolean isBlock) {
        this.userId= userId;
        this.fullName = fullName;
        this.ownerImage = userImage;
        this.phoneNumber = phoneNumber;
        this.wishId= wishId;
        this.nameWish = nameWish;
        this.wishImage = wishImage;
        this.nameHoliday = nameHoliday;
        this.dateOfHoliday = dateOfHoliday;
        this.statusWish = statusWish;
        this.isBlock = isBlock;
    }

    public ComplaintResponse(Long userId, String fullName, String ownerImage, Long charityId, String charityImage, String charityName, Condition condition,String reservoirImage, String createdAt) {
        this.userId = userId;
        this.fullName = fullName;
        this.ownerImage = ownerImage;
        this.charityId = charityId;
        this.charityImage = charityImage;
        this.charityName = charityName;
        this.condition = condition;
        this.reservoirImage=reservoirImage;
        this.createdAt = createdAt;
    }

    public ComplaintResponse(Long userId,String fullName, String userImage,Long wishId, String nameWish, String wishImage, String nameHoliday, LocalDate dateOfHoliday,boolean isBlock,Status statusWish,String reservoirImage) {
        this.userId= userId;
        this.fullName = fullName;
        this.ownerImage = userImage;
        this.wishId= wishId;
        this.nameWish = nameWish;
        this.wishImage = wishImage;
        this.nameHoliday = nameHoliday;
        this.dateOfHoliday = dateOfHoliday;
        this.isBlock=isBlock;
        this.statusWish=statusWish;
        this.reservoirImage=reservoirImage;
    }
}