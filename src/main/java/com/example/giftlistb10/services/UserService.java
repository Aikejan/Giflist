package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.userInfo.UserChangePasswordRequest;
import com.example.giftlistb10.dto.userInfo.ProfileRequest;
import com.example.giftlistb10.dto.userInfo.UserResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;

import java.util.List;

public interface UserService {
    SimpleResponse fillingProfile(ProfileRequest profileRequest);
    UserResponse getOwnProfile();
    UserResponse getProfileByUserId(Long userId);
    SimpleResponse changePassword(UserChangePasswordRequest changePassword);
    List<UserResponse> getAllUsers();
    SimpleResponse block(Long userId, boolean block);
    SimpleResponse deleteUser(Long userId);
    List<UserResponse> searchUserByName(String userName);
}