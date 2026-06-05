package com.staj.fooddelivery.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private String section;
    private boolean available;
    private Long restaurantId;
    private String restaurantName;
}
