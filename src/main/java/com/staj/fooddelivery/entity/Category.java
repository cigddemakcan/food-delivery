package com.staj.fooddelivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Kategori adı boş olamaz")
    @Column(nullable = false, unique = true)
    private String name;

    private String imageUrl;


    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Restaurant> restaurants = new ArrayList<>();
}
