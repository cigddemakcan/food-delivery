package com.staj.fooddelivery.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderRequest {

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "Restaurant id cannot be null")
    private Long restaurantId;

    // The address id the customer wants delivery to
    @NotNull(message = "Address id cannot be null")
    private Long addressId;

    private String note;            // Optional note to restaurant

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequest> items;
}
