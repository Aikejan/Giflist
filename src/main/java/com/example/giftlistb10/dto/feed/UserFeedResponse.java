package com.example.giftlistb10.dto.feed;

import com.example.giftlistb10.dto.charity.CharityResponse;
import com.example.giftlistb10.dto.holiday.HolidayResponse;
import com.example.giftlistb10.dto.wish.WishesResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedResponse {
    private List<WishesResponse> wishesResponses;
    private List<CharityResponse> charityResponses;
    private List<HolidayResponse> holidayResponses;
}