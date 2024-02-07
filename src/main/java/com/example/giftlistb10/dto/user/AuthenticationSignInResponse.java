package com.example.giftlistb10.dto.user;

import com.example.giftlistb10.enums.Role;
import lombok.Builder;

@Builder
public record AuthenticationSignInResponse(
        Long id,
        String fullName,
        String image,
        String token,
        String email,
        Role role
) {
}