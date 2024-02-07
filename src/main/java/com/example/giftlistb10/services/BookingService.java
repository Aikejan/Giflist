package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.booking.CharityBookingResponse;
import com.example.giftlistb10.dto.booking.WishBookingResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import java.util.List;

public interface BookingService {
    SimpleResponse bookingCharity(Long charityId,boolean reserveAnonymous);
    SimpleResponse bookingWish(Long charityId, boolean reserveAnonymous);
    SimpleResponse unBookingCharity(Long charityId);
    SimpleResponse unBookingWish(Long wihId);
    List<CharityBookingResponse> getAllReservedCharity();
    List<WishBookingResponse> getAllReservedWish();
}