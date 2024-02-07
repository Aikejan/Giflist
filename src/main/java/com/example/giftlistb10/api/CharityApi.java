package com.example.giftlistb10.api;

import com.example.giftlistb10.dto.charity.CharityRequest;
import com.example.giftlistb10.dto.charity.CharityResponse;
import com.example.giftlistb10.dto.simple.SimpleResponse;
import com.example.giftlistb10.enums.Category;
import com.example.giftlistb10.enums.Condition;
import com.example.giftlistb10.enums.Country;
import com.example.giftlistb10.enums.SubCategory;
import com.example.giftlistb10.services.CharityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/charity")
@RequiredArgsConstructor
@Tag(name = "Charity API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CharityApi {

    private final CharityService charityService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    @Operation(summary = "Сохранить благотворительность",
            description = "Сохраняет информацию о новой благотворительности.")
    public SimpleResponse saveCharity(@RequestBody @Valid CharityRequest charityRepose) {
        return charityService.saveCharity(charityRepose);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    @Operation(summary = "Получить все благотворительности",
            description = "Возвращает список всех доступных благотворительностей.")
    public List<CharityResponse> getAllCharities() {
        return charityService.getAllCharities();
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @DeleteMapping
    @Operation(summary = "Удалить благотворительность",
            description = "Удаляет благотворительность по её идентификатору.")
    public SimpleResponse deleteCharity(@RequestParam Long charityId) {
        return charityService.deleteCharityById(charityId);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping("/update")
    @Operation(summary = "Обновить благотворительность",
            description = "Обновляет информацию о существующей благотворительности.")
    public SimpleResponse updateCharity(@RequestParam Long charityId, @RequestBody CharityRequest charityRequest) {
        return charityService.updateCharity(charityId, charityRequest);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{charityId}")
    @Operation(summary = "Получить благотворительность по идентификатору",
            description = "Возвращает информацию о благотворительности по её идентификатору.")
    public Optional<CharityResponse> getCharityById(@PathVariable Long charityId) {
        return charityService.getCharityById(charityId);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Получить свои благотворительные организации",
            description = "Возвращает список благотворительных организаций, созданных текущим пользователем.")
    @GetMapping("/myCharities")
    public List<CharityResponse> getAllMyCharities(Long userId) {
        return charityService.getAllMyCharities(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "Получить благотворительные организации друзей пользователя",
            description = "Возвращает список благотворительных организаций, созданных друзьями пользователя.")
    @GetMapping("/myFriendsCharities/{userId}")
    public List<CharityResponse> getAllFriendsCharities(@PathVariable Long userId) {
        return charityService.getAllFriendsCharities(userId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/searchCharity")
    @Operation(summary = "Блогатворительность искать по имени и по перечисление",
            description = "Возвращает блогатворительность по имени и по перечисление")
    public List<CharityResponse> searchCharity(
            @RequestParam(required = false) String value,
            @RequestParam(required = false) Condition condition,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) SubCategory subCategory,
            @RequestParam(required = false) Country country) {
        return charityService.searchCharity(value, condition, category, subCategory, country);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{charityId}")
    @Operation(summary = "Метод для блокировки или разблокировки благотворительности по её идентификатору")
    public SimpleResponse blockOrUnblockCharity(@PathVariable Long charityId,@RequestParam boolean blockCharity) {
        return charityService.blockCharityFromUser(charityId,blockCharity);
    }
}