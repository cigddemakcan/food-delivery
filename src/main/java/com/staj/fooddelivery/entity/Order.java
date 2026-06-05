package com.staj.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // Subtotal before delivery fee
    @Builder.Default
    private Double subtotal = 0.0;

    // Delivery fee snapshot at order time
    @Builder.Default
    private Double deliveryFee = 0.0;

    // subtotal + deliveryFee
    @Builder.Default
    private Double totalAmount = 0.0;

    // Note to the restaurant (e.g. "No onions please")
    private String note;

    // Delivery address snapshot (stored as plain text in case address is deleted later)
    private String deliveryAddress;

    // Which user placed the order (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Which restaurant fulfils the order (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // The items in this order (One-to-Many)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public enum OrderStatus {
        PENDING,     // Waiting for restaurant confirmation
        CONFIRMED,   // Restaurant accepted
        PREPARING,   // Being prepared in kitchen
        ON_THE_WAY,  // Out for delivery
        DELIVERED,   // Delivered to customer
        CANCELLED    // Cancelled
    }
}
