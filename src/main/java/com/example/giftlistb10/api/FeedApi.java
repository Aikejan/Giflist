package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.feed.UserFeedResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.services.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@Tag(name = "Feed Api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PermitAll
public class FeedApi {

    private final FeedService feedService;

    @Operation(summary = "Пользователь может присвоить себе пожелание с идентификатором", description = "Пользователи может присвоить пожелание")
    @PostMapping("/assign/{wishId}/{userId}")
    public SimpleResponse assignWishToUser(@PathVariable Long wishId,
                                           @PathVariable Long userId){
        return feedService.copyWishToUser(wishId,userId);
    }

    @Operation(summary = "Получить желания, благотворительность и праздники от друзей")
    @GetMapping("/feedResponse/{userId}")
    public UserFeedResponse feedResponse(@PathVariable Long userId) {
        return feedService.feedResponse(userId);
    }
}