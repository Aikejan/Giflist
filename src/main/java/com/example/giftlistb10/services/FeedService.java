package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.feed.UserFeedResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;

public interface FeedService {
    SimpleResponse copyWishToUser(Long wishId, Long userId);
    UserFeedResponse feedResponse(Long userId);
}