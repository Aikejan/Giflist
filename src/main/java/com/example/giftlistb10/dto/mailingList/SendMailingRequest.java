package com.example.giftlistb10.dto.mailingList;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class SendMailingRequest {

    private String image;
    private String nameMailing;
    private String text;

    public String createMessage(){
        return String.format(
                image,nameMailing,text
        );
    }
}