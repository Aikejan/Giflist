package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.complaint.ComplaintResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.enums.StatusComplaint;
import com.example.giftlistb10.services.ComplainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/complaint")
@RequiredArgsConstructor
@Tag(name = "Complaint API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ComplaintApi {
    private final ComplainService complaintService;

    @PermitAll
    @Operation(summary = "Отправить жалобу на благотворительность с идентификатором", description = "Все пользователи могут отправить")
    @PostMapping("/charity/{charityId}")
    public SimpleResponse sendComplaintToCharity(@PathVariable Long charityId, @RequestParam StatusComplaint status, @RequestParam(required = false) String text) {
        return complaintService.sendComplaintToCharity(charityId, status, text);
    }

    @PermitAll
    @Operation(summary = "Отправить жалобу на желание с идентификатором", description = "Все пользователи могут отправить")
    @PostMapping("/wish/{wishId}")
    public SimpleResponse sendComplaintToWish(@PathVariable Long wishId, @RequestParam StatusComplaint status, @RequestParam(required = false) String text) {
        return complaintService.sendComplaintToWish(wishId, status, text);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getCharitiesWithComplaints")
    @Operation(summary = "Получение жалоб на благотворительные организации", description = "Этот эндпоинт предоставляет список жалоб, связанных с благотворительными организациями.")
    public List<ComplaintResponse> getCharitiesWithComplaints() {
        return complaintService.getCharitiesWithComplaints();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getCharityComplaintById/{id}")
    @Operation(summary = "Получение конкретной жалобы на благотворительную организацию по идентификатору", description = "Этот эндпоинт предоставляет подробную информацию о конкретной жалобе на благотворительную организацию с указанным идентификатором.")
    public ComplaintResponse getCharityComplaintById(@PathVariable Long id) {
        return complaintService.getCharityComplaintById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getAllWishesWithComplaints")
    @Operation(summary = "Получение жалоб на все желания", description = "Этот эндпоинт предоставляет список жалоб, связанных со всеми желаниями, включая информацию о пользователях, желаниях и самих жалобах.")
    public List<ComplaintResponse> getAllWishesWithComplaints() {
        return complaintService.getAllWishesWithComplaints();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getWishComplaintById/{id}")
    @Operation(summary = "Получение конкретной жалобы на желание по идентификатору", description = "Этот эндпоинт предоставляет подробную информацию о конкретной жалобе на желание с указанным идентификатором.")
    public ComplaintResponse getWishComplaintById(@PathVariable Long id) {
        return complaintService.getWishComplaintById(id);
    }
}