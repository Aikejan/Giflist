package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.wish.WishesRequest;
import com.example.giftlistb10.dto.wish.WishesResponse;
import com.example.giftlistb10.services.WishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlists")
@Tag(name = "Wish Api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WishListApi {

    private final WishService wishService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Получить все пожелания", description = "Все пользователи могут видеть")
    @GetMapping
    List<WishesResponse> getAllWishes() {
        return wishService.getAllWishes();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @Operation(summary = "Сохранить желание", description = "Каждый пользователь может")
    @PostMapping("/{holidayId}")
    SimpleResponse saveWish(@PathVariable Long holidayId,
                            @RequestBody WishesRequest request) {
        return wishService.saveWish(holidayId,request);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Получить желание с идентификатором желания", description = "Желающие иметь удостоверение личности")
    @GetMapping("/{wishId}")
    WishesResponse getWishById(@PathVariable Long wishId) {
        return wishService.getWishById(wishId);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @Operation(summary = "Обновить желание с идентификатором ", description = "Пользователь может изменить свое пожелание")
    @PutMapping("/{wishId}/{holidayId}")
    SimpleResponse updateWish(@PathVariable Long wishId,
                              @PathVariable Long holidayId,
                              @RequestBody WishesRequest request) {
        return wishService.updateWish(wishId, holidayId, request);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Удалить желание с идентификатором", description = "Пользователь может удалить желание с идентификатором")
    @DeleteMapping("/{wishId}")
    SimpleResponse deleteWish(@PathVariable Long wishId) {
        return wishService.deleteWish(wishId);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Получить все подарки одного пользователя с идентификатором", description = "Только пользователь с использованием токена может видеть свои пожелания")
    @GetMapping("/user/{userId}")
    List<WishesResponse> getAllWishesOfUser(@PathVariable Long userId){
        return wishService.getAllWishesOfUser(userId);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Получите все пожелания от моих друзей")
    @GetMapping("/fromFriends/{userId}")
    List<WishesResponse> getAllWishesFromFriends(@PathVariable Long userId){
        return wishService.getAllWishesFromFriends(userId);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Поиск желания по названию")
    @GetMapping("/searchWishByName")
    List<WishesResponse> searchWishByName(@RequestParam String wishName){
        return wishService.searchWishByName(wishName);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/blockOrUnblock/{wishId}")
    SimpleResponse blockOrUnblock(@PathVariable Long wishId,
                                  @RequestParam boolean block){
        return wishService.blockOrUnblock(wishId,block);
    }
}