package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.holiday.HolidayRequest;
import com.example.giftlistb10.dto.holiday.HolidayResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.wish.WishesResponse;
import com.example.giftlistb10.services.HolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@Tag(name = "Holiday Api")
@PermitAll
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 5000)
public class HolidayAPI {

    private final HolidayService holidayService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/getHolidaysByUserId/{userId}")
    @Operation(summary = "Метод для получения всех плаготворительностей")
    public List<HolidayResponse> getAllHolidaysUserById(@PathVariable Long userId) {
        return holidayService.getAllHolidaysByUserId(userId);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping
    @Operation(summary = "Метод сохраняет праздник")
    public SimpleResponse saveHoliday(@RequestBody HolidayRequest holidayRequest) {
        return holidayService.saveHoliday(holidayRequest);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{holidayId}")
    @Operation(summary = "Метод позволяет получить праздник по идентификатору")
    public HolidayResponse getHolidayById(@PathVariable Long holidayId) {
        return holidayService.getHolidayById(holidayId);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PutMapping("/{holidayId}")
    @Operation(summary = "Метод позволяет изменить праздник")
    public SimpleResponse updateHolidayId(@PathVariable Long holidayId,
                                          @RequestBody HolidayRequest holidayRequest) {
        return holidayService.updateHoliday(holidayId, holidayRequest);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @DeleteMapping("/{holidayId}")
    @Operation(summary = " Метод удаляет праздник")
    public SimpleResponse deleteHolidayById(@PathVariable Long holidayId) {
        return holidayService.deleteHolidayById(holidayId);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/getAllWish/{holidayId}")
    @Operation(summary = "Метод позволяет получить все желания по идентификатору праздника")
    public List<WishesResponse> getAllWishesFromHoliday(@PathVariable Long holidayId) {
        return holidayService.getAllHolidayOfWishesById(holidayId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/getAllHolidayFrends/{userId}")
    @Operation(summary =" Метод позволяет получить все праздники по идентификатору друга" )
    public List<HolidayResponse> getAllHolidaysFriendsById( @PathVariable Long userId){
        return holidayService.getAllHolidaysFriendsById(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/searchHolidayByName")
    @Operation(summary ="Поиск праздника по названию" )
    public List<HolidayResponse> searchHolidayByName( @RequestParam String holidayName){
        return holidayService.searchHolidayByName(holidayName);
    }
}