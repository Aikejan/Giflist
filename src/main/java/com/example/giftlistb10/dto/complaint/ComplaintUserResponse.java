package com.example.giftlistb10.dto.complaint;

import com.example.giftlistb10.enums.StatusComplaint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ComplaintUserResponse {
    private Long complainUserId;
    private String complainUserFullName;
    private String complainUserInfoImage;
    private StatusComplaint statusComplaint;
    private String textComplain;
    private LocalDate createdAt;

    public ComplaintUserResponse(Long complainUserId, String complainUserFullName, String complainUserInfoImage,
                                 StatusComplaint statusComplaint, String textComplain,LocalDate createdAt) {
        this.complainUserId = complainUserId;
        this.complainUserFullName = complainUserFullName;
        this.complainUserInfoImage = complainUserInfoImage;
        this.statusComplaint = statusComplaint;
        this.textComplain = textComplain;
        this.createdAt = createdAt;
    }
}