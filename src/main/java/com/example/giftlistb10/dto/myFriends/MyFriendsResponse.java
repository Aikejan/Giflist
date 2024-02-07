package com.example.giftlistb10.dto.myFriends;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyFriendsResponse {
    private Long friendId;
    private String nameFriend;
    private int countWish;
    private int countHoliday;
    private String image;

    public MyFriendsResponse(Long friendId, String nameFriend, int countWish, int countHoliday, String image) {
        this.friendId = friendId;
        this.nameFriend = nameFriend;
        this.countWish = countWish;
        this.countHoliday = countHoliday;
        this.image = image;
    }
}