package com.staj.fooddelivery.repository;

import com.staj.fooddelivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCategoryId(Long categoryId);
    List<Restaurant> findByCityIgnoreCaseAndActiveTrue(String city);
    List<Restaurant> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    List<Restaurant> findByCategoryIdAndCityIgnoreCaseAndActiveTrue(Long categoryId, String city);
}
