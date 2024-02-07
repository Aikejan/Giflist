package com.example.giftlistb10.dto.holiday;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor

public class HolidayResponse {

    private  Long holidayId;
    private String nameHoliday;
    private LocalDate dateOfHoliday;
    private String image;
    private Long friendId;
    private  String fullName;
    private String friendImage;

    public HolidayResponse(Long holidayId, String nameHoliday, LocalDate dateOfHoliday,
                           String image, Long friendId, String fullName, String friendImage) {
        this.holidayId = holidayId;
        this.nameHoliday = nameHoliday;
        this.dateOfHoliday = dateOfHoliday;
        this.image = image;
        this.friendId = friendId;
        this.fullName = fullName;
        this.friendImage = friendImage;
    }

    public HolidayResponse(Long holidayId, String nameHoliday, LocalDate dateOfHoliday, String image) {
        this.holidayId = holidayId;
        this.nameHoliday = nameHoliday;
        this.dateOfHoliday = dateOfHoliday;
        this.image = image;
    }
}