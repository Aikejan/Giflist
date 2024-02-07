package com.example.giftlistb10.dto.charity;

import com.example.giftlistb10.enums.Category;
import com.example.giftlistb10.enums.Condition;
import com.example.giftlistb10.enums.SubCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CharityRequest {
    @NotBlank(message = "Field can't be empty")
    private String nameCharity;
    @NotNull(message = "Field can't be null")
    private Category category;
    @NotNull(message = "Field can't be null")
    private SubCategory subcategory;
    @NotBlank(message = "Field can't be empty")
    private String description;
    @NotBlank(message = "Field can't be empty")
    private String image;
    @NotNull(message = "Field can't be null")
    private Condition condition;

}