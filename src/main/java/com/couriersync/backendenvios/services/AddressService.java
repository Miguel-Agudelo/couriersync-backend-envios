package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.AddressRequestDTO;
import com.couriersync.backendenvios.dtos.AddressResponseDTO;

import java.util.List;

public interface AddressService {
    AddressResponseDTO createAddress(AddressRequestDTO requestDTO);
    List<AddressResponseDTO> getAllAddresses();
    AddressResponseDTO getById(Integer id);
}