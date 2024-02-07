package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.wish.WishesRequest;
import com.example.giftlistb10.dto.wish.WishesResponse;
import java.util.List;

public interface WishService {
    List<WishesResponse> getAllWishes();

    WishesResponse getWishById(Long id);

    SimpleResponse saveWish(Long holidayId,WishesRequest request);

    SimpleResponse updateWish(Long id, Long holidayId, WishesRequest request);

    SimpleResponse deleteWish(Long id);

    List<WishesResponse> getAllWishesOfUser(Long userId);

    List<WishesResponse> getAllWishesFromFriends(Long userId);
    List<WishesResponse> searchWishByName(String name);

    SimpleResponse blockOrUnblock(Long wishId, boolean block);
}