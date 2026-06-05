package com.staj.fooddelivery.controller;

import com.staj.fooddelivery.dto.request.AddressRequest;
import com.staj.fooddelivery.dto.response.AddressResponse;
import com.staj.fooddelivery.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // GET /api/users/1/addresses
    @GetMapping
    public ResponseEntity<List<AddressResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getByUser(userId));
    }

    // GET /api/users/1/addresses/3
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getById(@PathVariable Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    // POST /api/users/1/addresses
    @PostMapping
    public ResponseEntity<AddressResponse> create(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(userId, request));
    }

    // PUT /api/users/1/addresses/3
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(
            @PathVariable Long userId,
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.update(id, request));
    }

    // DELETE /api/users/1/addresses/3
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
