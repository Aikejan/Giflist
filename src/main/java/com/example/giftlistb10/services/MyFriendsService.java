package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.myFriends.MyFriendsResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.enums.Request;
import java.util.List;

public interface MyFriendsService {
    List<MyFriendsResponse> getAllUserRequest();
    SimpleResponse sendRequestToFriend(Long userId);
    SimpleResponse acceptRequest(Long friendId, Request request);
    SimpleResponse deleteUserFromFriend(Long friendId);
    List<MyFriendsResponse> getAllUserFriends(Long userId);
}