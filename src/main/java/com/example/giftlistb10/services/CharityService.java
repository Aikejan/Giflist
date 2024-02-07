package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.charity.CharityRequest;
import com.example.giftlistb10.dto.charity.CharityResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.enums.Category;
import com.example.giftlistb10.enums.Condition;
import com.example.giftlistb10.enums.Country;
import com.example.giftlistb10.enums.SubCategory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface CharityService {
    SimpleResponse saveCharity(CharityRequest charityRequest);
    List<CharityResponse> getAllCharities();
    SimpleResponse deleteCharityById(Long charityId);
    SimpleResponse updateCharity(Long charityId, CharityRequest updatedCharityRequest);
    Optional<CharityResponse> getCharityById(Long charityId);
    List<CharityResponse> getAllMyCharities(Long userId);
    List<CharityResponse> getAllFriendsCharities(Long userId);
    List<CharityResponse> searchCharity(String value, Condition condition, Category category, SubCategory subCategory, Country country);
    SimpleResponse blockCharityFromUser(Long charityId,boolean blockCharity);
}