package com.staj.fooddelivery.service;

import com.staj.fooddelivery.dto.request.ProductRequest;
import com.staj.fooddelivery.dto.response.ProductResponse;
import com.staj.fooddelivery.entity.Product;
import com.staj.fooddelivery.entity.Restaurant;
import com.staj.fooddelivery.exception.ResourceNotFoundException;
import com.staj.fooddelivery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantService restaurantService;

    // Full menu of a restaurant
    @Transactional(readOnly = true)
    public List<ProductResponse> getByRestaurant(Long restaurantId) {
        return productRepository.findByRestaurantId(restaurantId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Only available products (shown to customers)
    @Transactional(readOnly = true)
    public List<ProductResponse> getAvailableByRestaurant(Long restaurantId) {
        return productRepository.findByRestaurantIdAndAvailableTrue(restaurantId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Filter by section (e.g. "Burgers")
    @Transactional(readOnly = true)
    public List<ProductResponse> getBySection(Long restaurantId, String section) {
        return productRepository.findByRestaurantIdAndSection(restaurantId, section).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return toResponse(findById(id));
    }

    public ProductResponse create(Long restaurantId, ProductRequest request) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .price(request.getPrice())
                .section(request.getSection())
                .available(request.isAvailable())
                .restaurant(restaurant)
                .build();
        return toResponse(productRepository.save(product));
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findById(id);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setPrice(request.getPrice());
        product.setSection(request.getSection());
        product.setAvailable(request.isAvailable());
        return toResponse(productRepository.save(product));
    }

    public void delete(Long id) {
        productRepository.delete(findById(id));
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .imageUrl(p.getImageUrl())
                .price(p.getPrice())
                .section(p.getSection())
                .available(p.isAvailable())
                .restaurantId(p.getRestaurant().getId())
                .restaurantName(p.getRestaurant().getName())
                .build();
    }
}
