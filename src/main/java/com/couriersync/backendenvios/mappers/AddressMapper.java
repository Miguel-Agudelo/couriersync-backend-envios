package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.controllers.AddressController;
import com.couriersync.backendenvios.dtos.AddressRequestDTO;
import com.couriersync.backendenvios.dtos.AddressResponseDTO;
import com.couriersync.backendenvios.entities.Address;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
        dto.add(linkTo(methodOn(AddressController.class).getById(address.getId())).withSelfRel());
        dto.add(linkTo(methodOn(AddressController.class).getAllAddresses()).withRel("addresses"));
        return dto;
    }

    public static List<AddressResponseDTO> FromEntityListToDtoList(List<Address> addresses) {
        return addresses.stream()
                .map(AddressMapper::FromEntityToDto)
                .collect(Collectors.toList());
    }
}