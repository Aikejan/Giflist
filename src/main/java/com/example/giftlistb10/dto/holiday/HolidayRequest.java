package com.example.giftlistb10.dto.holiday;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class HolidayRequest {
    private String nameHoliday;
    private LocalDate dateOfHoliday;
    private String image;

    public HolidayRequest( String nameHoliday, LocalDate dateOfHoliday, String image) {
        this.nameHoliday = nameHoliday;
        this.dateOfHoliday = dateOfHoliday;
        this.image = image;
    }
}