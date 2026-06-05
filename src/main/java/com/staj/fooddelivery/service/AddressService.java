package com.staj.fooddelivery.service;

import com.staj.fooddelivery.dto.request.AddressRequest;
import com.staj.fooddelivery.dto.response.AddressResponse;
import com.staj.fooddelivery.entity.Address;
import com.staj.fooddelivery.entity.User;
import com.staj.fooddelivery.exception.ResourceNotFoundException;
import com.staj.fooddelivery.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<AddressResponse> getByUser(Long userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AddressResponse getById(Long id) {
        return toResponse(findById(id));
    }

    public AddressResponse create(Long userId, AddressRequest request) {
        User user = userService.findById(userId);
        Address address = Address.builder()
                .title(request.getTitle())
                .city(request.getCity())
                .district(request.getDistrict())
                .street(request.getStreet())
                .buildingNo(request.getBuildingNo())
                .apartmentNo(request.getApartmentNo())
                .directions(request.getDirections())
                .user(user)
                .build();
        return toResponse(addressRepository.save(address));
    }

    public AddressResponse update(Long id, AddressRequest request) {
        Address address = findById(id);
        address.setTitle(request.getTitle());
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setStreet(request.getStreet());
        address.setBuildingNo(request.getBuildingNo());
        address.setApartmentNo(request.getApartmentNo());
        address.setDirections(request.getDirections());
        return toResponse(addressRepository.save(address));
    }

    public void delete(Long id) {
        addressRepository.delete(findById(id));
    }

    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", id));
    }

    private AddressResponse toResponse(Address a) {
        return AddressResponse.builder()
                .id(a.getId())
                .title(a.getTitle())
                .city(a.getCity())
                .district(a.getDistrict())
                .street(a.getStreet())
                .buildingNo(a.getBuildingNo())
                .apartmentNo(a.getApartmentNo())
                .directions(a.getDirections())
                .userId(a.getUser().getId())
                .build();
    }
}
