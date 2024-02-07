package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.holiday.HolidayRequest;
import com.example.giftlistb10.dto.holiday.HolidayResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.dto.wish.WishesResponse;
import java.util.List;

public interface HolidayService {
    SimpleResponse saveHoliday(HolidayRequest holidayRequest);
    List<HolidayResponse> getAllHolidaysByUserId(Long userId);
    HolidayResponse getHolidayById(Long holidayId);
    SimpleResponse updateHoliday(Long holidayId, HolidayRequest holidayRequest);
    SimpleResponse deleteHolidayById(Long holidayId);
    List<WishesResponse> getAllHolidayOfWishesById(Long id);
    List<HolidayResponse> getAllHolidaysFriendsById(Long userId);
    List<HolidayResponse> searchHolidayByName(String wishName);

}