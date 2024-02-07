package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.mailingList.MailingResponse;
import com.example.giftlistb10.dto.mailingList.SendMailingRequest;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.services.MailingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/mailings")
@RequiredArgsConstructor
@Tag(name="Mailing List Api")
@CrossOrigin(origins = "*",maxAge = 3600)
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class MailingListApi {

    private final MailingService mailingService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Отправить рассылку", description = "Только Админ может отправить рассылку")
    @PostMapping("/send")
    public SimpleResponse send(@RequestBody SendMailingRequest sendMailingRequest) throws IOException {
        return mailingService.sendToAll(sendMailingRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Список рассылок", description = "Список рассылок")
    @GetMapping
    public List<MailingResponse> getAllMailings(){
        return mailingService.getAllMailings();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public MailingResponse getMailingById(@PathVariable Long id){
        return mailingService.getById(id);
    }
}