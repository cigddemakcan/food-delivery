package com.staj.fooddelivery.controller;

import com.staj.fooddelivery.dto.request.OrderRequest;
import com.staj.fooddelivery.dto.response.OrderResponse;
import com.staj.fooddelivery.entity.Order;
import com.staj.fooddelivery.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // GET /api/orders/user/1  → Müşterinin sipariş geçmişi
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getByUser(userId));
    }

    // GET /api/orders/restaurant/1  → Restoranın gelen siparişleri
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponse>> getByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getByRestaurant(restaurantId));
    }

    // GET /api/orders/restaurant/1/status?value=PENDING  → Duruma göre filtrele
    @GetMapping("/restaurant/{restaurantId}/status")
    public ResponseEntity<List<OrderResponse>> getByRestaurantAndStatus(
            @PathVariable Long restaurantId,
            @RequestParam Order.OrderStatus value) {
        return ResponseEntity.ok(orderService.getByRestaurantAndStatus(restaurantId, value));
    }

    // GET /api/orders/1
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    // POST /api/orders  → Yeni sipariş ver
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    // PATCH /api/orders/1/status?value=CONFIRMED  → Restoran durumu güncelle
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus value) {
        return ResponseEntity.ok(orderService.updateStatus(id, value));
    }

    // PATCH /api/orders/1/cancel  → Müşteri iptal eder (sadece PENDING)
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancel(id));
    }
}
