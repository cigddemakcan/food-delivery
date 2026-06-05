package com.staj.fooddelivery.repository;

import com.staj.fooddelivery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByRestaurantId(Long restaurantId);
    List<Product> findByRestaurantIdAndAvailableTrue(Long restaurantId);
    List<Product> findByRestaurantIdAndSection(Long restaurantId, String section);
}
