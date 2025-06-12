package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.AddressRequestDTO;
import com.couriersync.backendenvios.dtos.AddressResponseDTO;
import com.couriersync.backendenvios.entities.Address;
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
        return new AddressResponseDTO(savedAddress.getId(), savedAddress.getCity(), savedAddress.getAddress());
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(address -> new AddressResponseDTO(address.getId(), address.getCity(), address.getAddress()))
                .collect(Collectors.toList());
    }
}