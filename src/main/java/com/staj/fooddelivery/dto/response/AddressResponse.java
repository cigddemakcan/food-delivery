package com.staj.fooddelivery.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressResponse {
    private Long id;
    private String title;
    private String city;
    private String district;
    private String street;
    private String buildingNo;
    private String apartmentNo;
    private String directions;
    private Long userId;

    // Human-readable full address string
    public String getFullAddress() {
        return String.format("%s No:%s D:%s, %s, %s/%s",
                street,
                buildingNo != null ? buildingNo : "-",
                apartmentNo != null ? apartmentNo : "-",
                district, city, city);
    }
}
