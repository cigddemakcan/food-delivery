package com.staj.fooddelivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Restaurant name cannot be blank")
    @Column(nullable = false)
    private String name;

    private String logoUrl;


    @NotBlank(message = "City cannot be blank")
    private String city;


    @PositiveOrZero
    @Builder.Default
    private Double minimumOrderAmount = 0.0;


    @Positive
    @Builder.Default
    private Integer estimatedDeliveryMinutes = 30;


    @PositiveOrZero
    @Builder.Default
    private Double deliveryFee = 0.0;


    @Builder.Default
    private Double rating = 0.0;

    @Builder.Default
    private boolean active = true;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
