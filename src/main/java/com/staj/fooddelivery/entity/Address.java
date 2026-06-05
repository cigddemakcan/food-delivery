package com.staj.fooddelivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Adres başlığı boş olamaz")
    private String title;

    @NotBlank(message = "Şehir boş olamaz")
    private String city;

    @NotBlank(message = "İlçe boş olamaz")
    private String district;

    @NotBlank(message = "Sokak bilgisi boş olamaz")
    private String street;

    private String buildingNo;

    private String apartmentNo;


    private String directions;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
