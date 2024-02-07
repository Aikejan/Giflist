package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.booking.CharityBookingResponse;
import com.example.giftlistb10.dto.booking.WishBookingResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
@Tag(name = "Booking API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookingApi {

    private final BookingService bookingService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{charityId}")
    @Operation(summary = "Бронирование благотворительности", description = "Вы можете забронировать благотворительность")
    public SimpleResponse bookingCharity(@PathVariable Long charityId,
                                         @RequestParam(name = "reserveAnonymous", defaultValue = "false") boolean reserveAnonymous) {
        return bookingService.bookingCharity(charityId, reserveAnonymous);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/bookingWish/{wishId}")
    @Operation(summary = "Бронирование пожелание", description = "Вы можете забронировать пожелание")
    public SimpleResponse bookingWish(@PathVariable Long wishId,
                                           @RequestParam(name = "reserveAnonymous", defaultValue = "false") boolean reserveAnonymous) {
        return bookingService.bookingWish(wishId, reserveAnonymous);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/unBookingCharity/{charityId}")
    @Operation(summary = "Разбронирование благотворительности", description = "Вы можете разбронировать благотворительность")
    public SimpleResponse unBookingCharity(@PathVariable Long charityId) {
        return bookingService.unBookingCharity(charityId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/unBookingWish/{wishId}")
    @Operation(summary = "Разбронирование пожелания", description = "Вы можете разбронировать пожелание")
    public SimpleResponse unBookingWish(@PathVariable Long wishId) {return bookingService.unBookingWish(wishId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getAllReservedWish")
    @Operation(summary = "Заброниранные желания", description = "Вы можете получить все заброниранные Вами желания")
    public List<WishBookingResponse> getAllReservedWish() {
        return bookingService.getAllReservedWish();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getAllReservedCharity")
    @Operation(summary = "Заброниранные благотворительности", description = "Вы можете получить все заброниранные благотворительность")
    public List<CharityBookingResponse> getAllReservedCharity() {
        return bookingService.getAllReservedCharity();
    }
}