package com.staj.fooddelivery.service;

import com.staj.fooddelivery.dto.request.OrderItemRequest;
import com.staj.fooddelivery.dto.request.OrderRequest;
import com.staj.fooddelivery.dto.response.OrderItemResponse;
import com.staj.fooddelivery.dto.response.OrderResponse;
import com.staj.fooddelivery.entity.*;
import com.staj.fooddelivery.exception.ResourceNotFoundException;
import com.staj.fooddelivery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final ProductService productService;
    private final AddressService addressService;

    // Customer's order history
    @Transactional(readOnly = true)
    public List<OrderResponse> getByUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Restaurant's incoming orders panel
    @Transactional(readOnly = true)
    public List<OrderResponse> getByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantIdOrderByCreatedAtDesc(restaurantId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Filter restaurant orders by status (e.g. show only PENDING)
    @Transactional(readOnly = true)
    public List<OrderResponse> getByRestaurantAndStatus(Long restaurantId, Order.OrderStatus status) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(Long id) {
        return toResponse(findById(id));
    }

    public OrderResponse create(OrderRequest request) {
        User user = userService.findById(request.getUserId());
        Restaurant restaurant = restaurantService.findById(request.getRestaurantId());
        Address address = addressService.findById(request.getAddressId());

        // Validate address belongs to the user
        if (!address.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Address does not belong to this user");
        }

        // Snapshot delivery address so it's preserved even if address is deleted later
        String deliveryAddressSnapshot = String.format("%s, %s No:%s D:%s, %s, %s",
                address.getStreet(),
                address.getBuildingNo() != null ? address.getBuildingNo() : "-",
                address.getApartmentNo() != null ? address.getApartmentNo() : "-",
                address.getDistrict(),
                address.getCity(),
                address.getDirections() != null ? "(" + address.getDirections() + ")" : ""
        );

        Order order = Order.builder()
                .user(user)
                .restaurant(restaurant)
                .note(request.getNote())
                .deliveryAddress(deliveryAddressSnapshot)
                .deliveryFee(restaurant.getDeliveryFee())
                .orderItems(new ArrayList<>())
                .build();

        // Build order items and calculate subtotal
        double subtotal = 0.0;
        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productService.findById(itemReq.getProductId());

            // Product must belong to the same restaurant
            if (!product.getRestaurant().getId().equals(restaurant.getId())) {
                throw new IllegalArgumentException(
                        "Product '" + product.getName() + "' does not belong to this restaurant");
            }

            // Product must be available
            if (!product.isAvailable()) {
                throw new IllegalArgumentException(
                        "Product '" + product.getName() + "' is currently unavailable");
            }

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .unitPrice(product.getPrice())
                    .order(order)
                    .build();

            order.getOrderItems().add(item);
            subtotal += item.getSubtotal();
        }

        // Enforce minimum order amount
        if (subtotal < restaurant.getMinimumOrderAmount()) {
            throw new IllegalArgumentException(
                    "Order subtotal (" + subtotal + ") is below the minimum order amount ("
                    + restaurant.getMinimumOrderAmount() + ")");
        }

        order.setSubtotal(subtotal);
        order.setTotalAmount(subtotal + restaurant.getDeliveryFee());

        return toResponse(orderRepository.save(order));
    }

    // Restaurant updates order status (PENDING → CONFIRMED → PREPARING → ON_THE_WAY → DELIVERED)
    public OrderResponse updateStatus(Long id, Order.OrderStatus newStatus) {
        Order order = findById(id);
        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot update a cancelled order");
        }
        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("Order is already delivered");
        }
        order.setStatus(newStatus);
        return toResponse(orderRepository.save(order));
    }

    // Customer can only cancel a PENDING order
    public OrderResponse cancel(Long id) {
        Order order = findById(id);
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalArgumentException(
                    "Only PENDING orders can be cancelled. Current status: " + order.getStatus());
        }
        order.setStatus(Order.OrderStatus.CANCELLED);
        return toResponse(orderRepository.save(order));
    }

    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }

    private OrderResponse toResponse(Order o) {
        List<OrderItemResponse> items = o.getOrderItems().stream()
                .map(oi -> OrderItemResponse.builder()
                        .id(oi.getId())
                        .productId(oi.getProduct().getId())
                        .productName(oi.getProduct().getName())
                        .productImageUrl(oi.getProduct().getImageUrl())
                        .quantity(oi.getQuantity())
                        .unitPrice(oi.getUnitPrice())
                        .subtotal(oi.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(o.getId())
                .status(o.getStatus())
                .createdAt(o.getCreatedAt())
                .subtotal(o.getSubtotal())
                .deliveryFee(o.getDeliveryFee())
                .totalAmount(o.getTotalAmount())
                .note(o.getNote())
                .deliveryAddress(o.getDeliveryAddress())
                .userId(o.getUser().getId())
                .userName(o.getUser().getFullName())
                .restaurantId(o.getRestaurant().getId())
                .restaurantName(o.getRestaurant().getName())
                .items(items)
                .build();
    }
}
