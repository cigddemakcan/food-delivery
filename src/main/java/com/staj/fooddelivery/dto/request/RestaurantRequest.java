package com.staj.fooddelivery.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RestaurantRequest {

    @NotBlank(message = "Restaurant name cannot be blank")
    private String name;

    private String logoUrl;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @PositiveOrZero(message = "Minimum order amount must be zero or positive")
    private Double minimumOrderAmount;

    @Positive(message = "Estimated delivery minutes must be positive")
    private Integer estimatedDeliveryMinutes;

    @PositiveOrZero(message = "Delivery fee must be zero or positive")
    private Double deliveryFee;

    @NotNull(message = "Category id cannot be null")
    private Long categoryId;
}
