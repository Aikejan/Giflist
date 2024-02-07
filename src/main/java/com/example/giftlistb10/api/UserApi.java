package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.userInfo.UserChangePasswordRequest;
import com.example.giftlistb10.dto.userInfo.ProfileRequest;
import com.example.giftlistb10.dto.userInfo.UserResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserApi {

    private final UserService userService;

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    @Operation(summary = "Метод для заполнения профиля")
    public SimpleResponse fillingProfile(@RequestBody @Valid ProfileRequest profileRequest) {
        return userService.fillingProfile(profileRequest);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping()
    @Operation(summary = "Метод для получения вашего профиля")
    public UserResponse getOwnProfile() {
        return userService.getOwnProfile();
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{userId}")
    @Operation(summary = "Метод для получения профиля пользователя")
    public UserResponse getProfileByUserId(@PathVariable Long userId) {
        return userService.getProfileByUserId(userId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/changePassword")
    @Operation(summary = "Метод для изменения вашего пароля")
    public SimpleResponse changePassword(@RequestBody @Valid UserChangePasswordRequest changePassword) {
        return userService.changePassword(changePassword);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllUsers")
    @Operation(summary = "Метод для получения всех пользователей")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/block/{userId}")
    @Operation(summary = "Метод для блокировки или разблокировки пользователя по его идентификатору")
    public SimpleResponse blockOrUnblockUser(@PathVariable Long userId,@RequestParam boolean block) {
        return userService.block(userId, block);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteUser/{userId}")
    @Operation(summary = "Метод для удаления пользователя по его идентификатору")
    public SimpleResponse deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/searchUser")
    @Operation(summary = "Поиск пользователя по имени")
    public List<UserResponse> searchUserByName(@RequestParam String userName) {
        return userService.searchUserByName(userName);
    }
}