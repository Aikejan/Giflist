package com.example.giftlistb10.services.servicesImpl;

import com.example.giftlistb10.dto.holiday.HolidayRequest;
import com.example.giftlistb10.dto.holiday.HolidayResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.wish.WishesResponse;
import com.example.giftlistb10.entities.Holiday;
import com.example.giftlistb10.entities.User;
import com.example.giftlistb10.exceptions.BadCredentialException;
import com.example.giftlistb10.exceptions.NotFoundException;
import com.example.giftlistb10.repositories.HolidayRepository;
import com.example.giftlistb10.repositories.UserRepository;
import com.example.giftlistb10.repositories.jdbcTemplate.HolidayJDBCTemplate;
import com.example.giftlistb10.services.HolidayService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
@Slf4j
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;
    private final UserRepository userRepository;
    private final HolidayJDBCTemplate holidayJDBCTemplate;

    @Override
    public SimpleResponse saveHoliday(HolidayRequest holidayRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialException("У вас доступа нет!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> {
            log.info("User with email: " + email + " not found!");
            return new NotFoundException(String.format("Пользователь с адресом электронной почты: %s не найден!",email));
        });

        Holiday holiday = new Holiday();
        holiday.setNameHoliday(holidayRequest.getNameHoliday());
        holiday.setDateOfHoliday(holidayRequest.getDateOfHoliday());
        holiday.setImage(holidayRequest.getImage());
        holiday.setUser(user);
        holidayRepository.save(holiday);
        log.info("Holiday is successfully saved");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Праздник с идентификатором: " + holiday.getId() + " успешно сохранен")
                .build();
    }

    @Override
    public List<HolidayResponse> getAllHolidaysByUserId(Long userId) {
        return holidayJDBCTemplate.getAllHolidaysUserById(userId);
    }

    @Override
    public HolidayResponse getHolidayById(Long holidayId) {
        return holidayJDBCTemplate.getHolidayById(holidayId);
    }

    @Override
    public SimpleResponse updateHoliday(Long holidayId, HolidayRequest holidayRequest) {
        Holiday holiday = holidayRepository.findById(holidayId).orElseThrow(() -> {
            log.info("User with holidayId: " + holidayId + " not found!");
            return new NotFoundException("Праздник с идентификатором: " + holidayId + " не найден");
        });
        holiday.setNameHoliday(holidayRequest.getNameHoliday());
        holiday.setDateOfHoliday(holidayRequest.getDateOfHoliday());
        holiday.setImage(holidayRequest.getImage());
        holidayRepository.save(holiday);
        log.info("Holiday is successfully updated");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Праздник с идентификатором: " + holidayId + " изменен")
                .build();
    }

    @Override
    public SimpleResponse deleteHolidayById(Long holidayId) {
        holidayRepository.findById(holidayId).orElseThrow(() -> {
                    log.info("Holiday with id: " + holidayId + " not found!");
                    return new NotFoundException("Праздник c id: " + holidayId + " не найден");
                });
        holidayRepository.deleteById(holidayId);
        log.info("Holiday is successfully deleted");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Праздник с идентификатором: " + holidayId + " успешно удален")
                .build();
    }

    @Override
    public List<WishesResponse> getAllHolidayOfWishesById(Long id) {
        return holidayJDBCTemplate.getAllHolidayOfWishesById(id);
    }

    @Override
    public List<HolidayResponse> getAllHolidaysFriendsById(Long userId) {
        return holidayJDBCTemplate.getAllHolidaysFriendsById(userId);
    }

    @Override
    public List<HolidayResponse> searchHolidayByName(String wishName) {
        return holidayJDBCTemplate.searchHolidayByName(wishName);
    }
}