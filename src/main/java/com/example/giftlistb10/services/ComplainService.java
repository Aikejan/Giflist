package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.complaint.ComplaintResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.enums.StatusComplaint;
import java.util.List;

public interface ComplainService {
     List<ComplaintResponse> getCharitiesWithComplaints();
     ComplaintResponse getCharityComplaintById(Long charityId);
     List<ComplaintResponse> getAllWishesWithComplaints();
     ComplaintResponse getWishComplaintById(Long wishId);
    SimpleResponse sendComplaintToWish(Long wishId, StatusComplaint status, String text);
    SimpleResponse sendComplaintToCharity(Long charityId, StatusComplaint status, String text);
}