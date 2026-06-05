package com.staj.fooddelivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;       // e.g. "Home", "Work"

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "District cannot be blank")
    private String district;

    @NotBlank(message = "Street cannot be blank")
    private String street;

    private String buildingNo;
    private String apartmentNo;
    private String directions;  // Extra courier directions
}
