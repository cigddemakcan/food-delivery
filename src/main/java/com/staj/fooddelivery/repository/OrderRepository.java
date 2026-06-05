package com.staj.fooddelivery.repository;

import com.staj.fooddelivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Customer's order history
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    // Restaurant's incoming orders
    List<Order> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);
    // Filter restaurant orders by status
    List<Order> findByRestaurantIdAndStatus(Long restaurantId, Order.OrderStatus status);
}
