package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.config.JwtService;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.user.*;
import com.example.giftlistb10.entities.User;
import com.example.giftlistb10.entities.UserInfo;
import com.example.giftlistb10.enums.ClothingSize;
import com.example.giftlistb10.enums.Country;
import com.example.giftlistb10.enums.Role;
import com.example.giftlistb10.enums.ShoeSize;
import com.example.giftlistb10.exceptions.AlreadyExistException;
import com.example.giftlistb10.exceptions.BadCredentialException;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.UserRepository;
import com.example.giftlistb10.services.AuthenticationService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationSignUpResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("Пользователь с адресом электронной почты:"
                    + request.getEmail() + " уже существует");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUserInfo(UserInfo.builder().image(null).build());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAgree(request.getIsAgree());
        user.setRole(Role.USER);
        userRepository.save(user);

        log.info("Пользователь успешно сохранен с идентификатором:" + user.getId());
        String token = jwtService.generateToken(user);
        return new AuthenticationSignUpResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                token,
                user.getEmail(),
                user.getRole()
        );
    }


    @Override
    public AuthenticationSignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с адресом электронной почты: %s не найден!", signInRequest.email())));

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            log.info("Недействительный пароль");
            throw new BadCredentialException("Недействительный пароль");
        }
        if (user.isBlock()) {
            log.info("Your account is blocked. You will unblocked after 7 days");
            throw new BadCredentialException("Ваш аккаунт был заблокирован");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password()));
        String token = jwtService.generateToken(user);
        return AuthenticationSignInResponse.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .image(user.getUserInfo().getImage())
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public SimpleResponse changeOnForgot(ForgotPasswordRequest newPasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Access Denied Exception !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> {
            log.error("User with email is not found !!!");
            return new NotFoundException("User is not found !!!");
        });
        if (newPasswordRequest.getNewPassword().equals(newPasswordRequest.getVerifyPassword())) {
            user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPassword()));
            userRepository.save(user);
            return new SimpleResponse(
                    HttpStatus.OK,
                    "Пароль успешно обновлен"
            );
        } else {
            return new SimpleResponse(
                    HttpStatus.BAD_REQUEST,
                    "Пожалуйста, удостоверьтесь, что введенные пароли идентичны"
            );
        }
    }

    @PostConstruct
    public void initSaveAdmin() {
        User user = User.builder()
                .firstName("Ulan")
                .lastName("Kubanychbekov")
                .email("admin@gmail.com")
                .userInfo(UserInfo.builder()
                        .dateOfBirth(LocalDate.of(1995, 4, 4))
                        .country(Country.KYRGYZSTAN)
                        .phoneNumber("+996705453456")
                        .linkFaceBook("linkTelgram")
                        .linkInstagram("linkInstagram")
                        .linkVkontakte("linkVkontakt")
                        .linkFaceBook("linkFacebook")
                        .clothingSize(ClothingSize.L)
                        .shoeSize(ShoeSize.FORTY_ONE)
                        .hobby("kara jorgo biyi")
                        .image("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTFuRCV6Vh5lC9aBWEXT4JEnCs399BGL7cYITbb3C1oUFZr6_8u")
                        .important("Не курю")
                        .build())
                .password(passwordEncoder.encode("Admin123_"))
                .role(Role.ADMIN)
                .build();
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
            log.info("Администратор успешно сохранен с идентификатором:" + user.getId());
        }
    }

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(new ClassPathResource("giftList.json").getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    public AuthenticationSignUpResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        User user;
        if (!userRepository.existsByEmail(firebaseToken.getEmail())) {
            User newUser = new User();
            String[] name = firebaseToken.getName().split(" ");
            newUser.setFirstName(name[0]);
            newUser.setLastName(name[1]);
            newUser.setUserInfo(UserInfo.builder().image(newUser.getUserInfo().getImage()).build());
            newUser.setEmail(firebaseToken.getEmail());
            newUser.setPassword(passwordEncoder.encode(firebaseToken.getEmail()));
            newUser.setRole(Role.USER);
            userRepository.save(newUser);
        }
        user = userRepository.getUserByEmail(firebaseToken.getEmail()).orElseThrow(() -> {
            log.info("Пользователь с адресом электронной почты: %s не найден!", firebaseToken.getEmail());
            throw new NotFoundException(String.format("Пользователь с адресом электронной почты: %s не найден!",
                    firebaseToken.getEmail()));
        });
        String token = jwtService.generateToken(user);
        return new AuthenticationSignUpResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getUserInfo().getImage(),
                token,
                user.getEmail(),
                user.getRole());
    }
}