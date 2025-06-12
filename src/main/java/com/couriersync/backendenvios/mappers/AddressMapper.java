package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.dtos.AddressRequestDTO;
import com.couriersync.backendenvios.dtos.AddressResponseDTO;
import com.couriersync.backendenvios.entities.Address;

public class AddressMapper {

    public static Address FromDtoToEntity(AddressRequestDTO dto) {
        Address address = new Address();
        address.setCity(dto.getCity());
        address.setAddress(dto.getAddress());
        return address;
    }

    public static AddressResponseDTO FromEntityToDto(Address address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setCity(address.getCity());
        dto.setAddress(address.getAddress());
        return dto;
    }
}