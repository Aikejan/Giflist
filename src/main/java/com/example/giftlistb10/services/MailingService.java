package com.example.giftlistb10.services;

import com.example.giftlistb10.dto.mailingList.MailingResponse;
import com.example.giftlistb10.dto.mailingList.SendMailingRequest;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

public interface MailingService {

    SimpleResponse sendToAll(SendMailingRequest sendMailingRequest) throws IOException;

    List<MailingResponse> getAllMailings();

    MailingResponse getById(Long mailingId);
}