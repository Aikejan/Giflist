package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.myFriends.MyFriendsResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.enums.Request;
import com.example.giftlistb10.services.MyFriendsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/myFriends")
@RequiredArgsConstructor
@Tag(name = "MyFriends API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MyFriendsApi {

    private final MyFriendsService myFriendsService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{friendId}")
    @Operation(summary = "Получить всех моих друзей", description = "Вы можете получить всех своих друзей")
    public List<MyFriendsResponse> getAllFriends(@PathVariable Long friendId) {
        return myFriendsService.getAllUserFriends(friendId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getAllRequest")
    @Operation(summary = "Получить все запросы от пользователей", description = "Вы можете получить все запросы от пользователей")
    public List<MyFriendsResponse> getAllUserRequest() {
        return myFriendsService.getAllUserRequest();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{friendId}")
    @Operation(summary = "Отправить запрос пользователю")
    public SimpleResponse sendRequestToFriend(@PathVariable Long friendId) {
       return myFriendsService.sendRequestToFriend(friendId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/acceptFriend/{friendId}")
    @Operation(summary = "Принять запрос от пользователя или отклонить")
    public SimpleResponse acceptRequest(@PathVariable Long friendId, @RequestParam Request request) {
        return myFriendsService.acceptRequest(friendId,request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{friendId}")
    @Operation(summary = "Удалить пользователя из друзей")
    public SimpleResponse deleteUserFromFriendById(@PathVariable Long friendId) {
       return myFriendsService.deleteUserFromFriend(friendId);
    }
}