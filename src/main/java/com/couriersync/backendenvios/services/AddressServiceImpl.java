package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.AddressRequestDTO;
import com.couriersync.backendenvios.dtos.AddressResponseDTO;
import com.couriersync.backendenvios.entities.Address;
import com.couriersync.backendenvios.exceptions.GlobalExceptionHandler;
import com.couriersync.backendenvios.mappers.AddressMapper;
import com.couriersync.backendenvios.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public AddressResponseDTO createAddress(AddressRequestDTO requestDTO) {
        Address address = new Address();
        address.setCity(requestDTO.getCity());
        address.setAddress(requestDTO.getAddress());
        Address savedAddress = addressRepository.save(address);
        return AddressMapper.FromEntityToDto(savedAddress);
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        return AddressMapper.FromEntityListToDtoList(addressRepository.findAll()); // <-- Usar nuevo método del mapper
    }

    @Override
    public AddressResponseDTO getById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Address with ID " + id + " not found."));
        return AddressMapper.FromEntityToDto(address);
    }
}