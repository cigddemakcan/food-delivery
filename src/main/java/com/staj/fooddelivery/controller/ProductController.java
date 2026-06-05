package com.staj.fooddelivery.controller;

import com.staj.fooddelivery.dto.request.ProductRequest;
import com.staj.fooddelivery.dto.response.ProductResponse;
import com.staj.fooddelivery.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // GET /api/restaurants/1/products  → Tüm ürünler (admin paneli)
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(productService.getByRestaurant(restaurantId));
    }

    // GET /api/restaurants/1/products/available  → Müşteriye gösterilen ürünler
    @GetMapping("/available")
    public ResponseEntity<List<ProductResponse>> getAvailable(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(productService.getAvailableByRestaurant(restaurantId));
    }

    // GET /api/restaurants/1/products/section?name=Burgers  → Bölüme göre filtrele
    @GetMapping("/section")
    public ResponseEntity<List<ProductResponse>> getBySection(
            @PathVariable Long restaurantId,
            @RequestParam String name) {
        return ResponseEntity.ok(productService.getBySection(restaurantId, name));
    }

    // GET /api/restaurants/1/products/5
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long restaurantId, @PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    // POST /api/restaurants/1/products
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @PathVariable Long restaurantId,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(restaurantId, request));
    }

    // PUT /api/restaurants/1/products/5
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long restaurantId,
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    // DELETE /api/restaurants/1/products/5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long restaurantId, @PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
