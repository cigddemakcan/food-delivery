package com.staj.fooddelivery.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private int restaurantCount;
}
