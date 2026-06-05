package com.staj.fooddelivery.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private String logoUrl;
    private String city;
    private Double minimumOrderAmount;
    private Integer estimatedDeliveryMinutes;
    private Double deliveryFee;
    private Double rating;
    private boolean active;
    private Long categoryId;
    private String categoryName;
    private int productCount;
}
