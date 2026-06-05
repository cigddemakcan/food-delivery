package com.staj.fooddelivery.controller;

import com.staj.fooddelivery.dto.request.RestaurantRequest;
import com.staj.fooddelivery.dto.response.RestaurantResponse;
import com.staj.fooddelivery.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    // GET /api/restaurants
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAll() {
        return ResponseEntity.ok(restaurantService.getAll());
    }

    // GET /api/restaurants/1
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getById(id));
    }

    // GET /api/restaurants/city?name=Ankara  → Ana sayfa gibi şehre göre listele
    @GetMapping("/city")
    public ResponseEntity<List<RestaurantResponse>> getByCity(@RequestParam String name) {
        return ResponseEntity.ok(restaurantService.getByCity(name));
    }

    // GET /api/restaurants/filter?categoryId=2&city=Ankara  → Kategori + şehir filtresi
    @GetMapping("/filter")
    public ResponseEntity<List<RestaurantResponse>> getByCategoryAndCity(
            @RequestParam Long categoryId,
            @RequestParam String city) {
        return ResponseEntity.ok(restaurantService.getByCategoryAndCity(categoryId, city));
    }

    // GET /api/restaurants/search?name=burger  → Restoran adına göre arama
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponse>> search(@RequestParam String name) {
        return ResponseEntity.ok(restaurantService.search(name));
    }

    // POST /api/restaurants
    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.create(request));
    }

    // PUT /api/restaurants/1
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok(restaurantService.update(id, request));
    }

    // PATCH /api/restaurants/1/deactivate  → Silmek yerine pasife al
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        restaurantService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
