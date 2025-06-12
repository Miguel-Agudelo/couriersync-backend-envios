package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.ClientRequestDTO;
import com.couriersync.backendenvios.dtos.ClientResponseDTO;

import java.util.List;

public interface ClientService {
    ClientResponseDTO createClient(ClientRequestDTO requestDTO);
    List<ClientResponseDTO> getAllClients();
}