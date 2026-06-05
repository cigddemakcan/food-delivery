package com.staj.fooddelivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryRequest {

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private String imageUrl;
}
