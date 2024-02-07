package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.user.*;
import com.google.firebase.auth.FirebaseAuthException;

public interface AuthenticationService {
    AuthenticationSignUpResponse signUp(SignUpRequest request);
    AuthenticationSignInResponse signIn(SignInRequest signInRequest);
    AuthenticationSignUpResponse authWithGoogle(String tokenId)throws FirebaseAuthException;
    SimpleResponse changeOnForgot(ForgotPasswordRequest password);
}