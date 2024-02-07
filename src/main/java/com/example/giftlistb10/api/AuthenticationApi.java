package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.user.*;
import com.example.giftlistb10.services.AuthenticationService;
import com.example.giftlistb10.services.emailSender.EmailService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@CrossOrigin(origins = "*", maxAge = 3600)
@PermitAll
public class AuthenticationApi {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    @PostMapping("/signIn")
    @Operation(summary = "Вход в свой аккаунт")
    public AuthenticationSignInResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping("/signUp")
    @Operation(summary = "Зарегистрироваться", description = "Регистрация  аккаунта")
    public AuthenticationSignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/auth-google")
    @Operation(summary = "Регистрация с помощью Google")
    public AuthenticationSignUpResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        return authenticationService.authWithGoogle(tokenId);
    }

    @Operation(summary = "Забыли пароль", description = "Отправить ссылку пользователю на email для смены пароля")
    @PutMapping("/forgotPassword")
    public SimpleResponse forgotPassword(@RequestParam String email, @RequestParam String linkToChangePassword){
        return emailService.sendHtmlMessage(email, linkToChangePassword);
    }

    @Operation(summary = "Изменить пароль", description = "Метод для смены пароля")
    @PutMapping("/changePassword")
    public SimpleResponse changePasswordOnForgot(@RequestBody @Valid ForgotPasswordRequest forgotPassword) {
        return authenticationService.changeOnForgot(forgotPassword);
    }
}