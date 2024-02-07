package com.example.giftlistb10.dto.wish;

import com.example.giftlistb10.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class WishesRequest {
    private String nameWish;
    private String image;
    private String linkToGift;
    private String description;
    private Status status;

    public WishesRequest(String nameWish, String image, String linkToGift, String description, Status status) {
        this.nameWish = nameWish;
        this.image = image;
        this.linkToGift = linkToGift;
        this.description = description;
        this.status = status;
    }
}