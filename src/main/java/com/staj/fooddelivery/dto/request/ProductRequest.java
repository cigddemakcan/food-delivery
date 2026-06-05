package com.staj.fooddelivery.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductRequest {

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    private String description;
    private String imageUrl;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    private String section;         // e.g. "Burgers", "Drinks"

    @Builder.Default
    private boolean available = true;
}
