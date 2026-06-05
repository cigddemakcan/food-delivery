package com.staj.fooddelivery.dto.response;

import com.staj.fooddelivery.entity.Order;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
    private Long id;
    private Order.OrderStatus status;
    private LocalDateTime createdAt;
    private Double subtotal;
    private Double deliveryFee;
    private Double totalAmount;
    private String note;
    private String deliveryAddress;
    private Long userId;
    private String userName;
    private Long restaurantId;
    private String restaurantName;
    private List<OrderItemResponse> items;
}
