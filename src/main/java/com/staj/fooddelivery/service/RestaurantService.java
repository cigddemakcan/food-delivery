package com.staj.fooddelivery.service;

import com.staj.fooddelivery.dto.request.RestaurantRequest;
import com.staj.fooddelivery.dto.response.RestaurantResponse;
import com.staj.fooddelivery.entity.Category;
import com.staj.fooddelivery.entity.Restaurant;
import com.staj.fooddelivery.exception.ResourceNotFoundException;
import com.staj.fooddelivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    public List<RestaurantResponse> getAll() {
        return restaurantRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RestaurantResponse getById(Long id) {
        return toResponse(findById(id));
    }

    // Browse restaurants by city (like Trendyol Yemek home screen)
    @Transactional(readOnly = true)
    public List<RestaurantResponse> getByCity(String city) {
        return restaurantRepository.findByCityIgnoreCaseAndActiveTrue(city).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Filter by category + city (e.g. "Show me all Burger places in Ankara")
    @Transactional(readOnly = true)
    public List<RestaurantResponse> getByCategoryAndCity(Long categoryId, String city) {
        return restaurantRepository.findByCategoryIdAndCityIgnoreCaseAndActiveTrue(categoryId, city).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Search by name
    @Transactional(readOnly = true)
    public List<RestaurantResponse> search(String name) {
        return restaurantRepository.findByNameContainingIgnoreCaseAndActiveTrue(name).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RestaurantResponse create(RestaurantRequest request) {
        Category category = categoryService.findById(request.getCategoryId());
        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .logoUrl(request.getLogoUrl())
                .city(request.getCity())
                .minimumOrderAmount(request.getMinimumOrderAmount() != null ? request.getMinimumOrderAmount() : 0.0)
                .estimatedDeliveryMinutes(request.getEstimatedDeliveryMinutes() != null ? request.getEstimatedDeliveryMinutes() : 30)
                .deliveryFee(request.getDeliveryFee() != null ? request.getDeliveryFee() : 0.0)
                .category(category)
                .build();
        return toResponse(restaurantRepository.save(restaurant));
    }

    public RestaurantResponse update(Long id, RestaurantRequest request) {
        Restaurant restaurant = findById(id);
        Category category = categoryService.findById(request.getCategoryId());
        restaurant.setName(request.getName());
        restaurant.setLogoUrl(request.getLogoUrl());
        restaurant.setCity(request.getCity());
        restaurant.setMinimumOrderAmount(request.getMinimumOrderAmount());
        restaurant.setEstimatedDeliveryMinutes(request.getEstimatedDeliveryMinutes());
        restaurant.setDeliveryFee(request.getDeliveryFee());
        restaurant.setCategory(category);
        return toResponse(restaurantRepository.save(restaurant));
    }

    // Deactivate instead of hard-delete (safer for order history)
    public void deactivate(Long id) {
        Restaurant restaurant = findById(id);
        restaurant.setActive(false);
        restaurantRepository.save(restaurant);
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", id));
    }

    private RestaurantResponse toResponse(Restaurant r) {
        return RestaurantResponse.builder()
                .id(r.getId())
                .name(r.getName())
                .logoUrl(r.getLogoUrl())
                .city(r.getCity())
                .minimumOrderAmount(r.getMinimumOrderAmount())
                .estimatedDeliveryMinutes(r.getEstimatedDeliveryMinutes())
                .deliveryFee(r.getDeliveryFee())
                .rating(r.getRating())
                .active(r.isActive())
                .categoryId(r.getCategory() != null ? r.getCategory().getId() : null)
                .categoryName(r.getCategory() != null ? r.getCategory().getName() : null)
                .productCount(r.getProducts().size())
                .build();
    }
}
