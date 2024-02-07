package com.example.giftlistb10.dto.mailingList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailingResponse {
    private String image;
    private String nameMailing;
    private LocalDate createdAt;
    private Long mailingId;
    private String text;
}