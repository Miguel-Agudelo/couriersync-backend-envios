package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.dtos.ClientRequestDTO;
import com.couriersync.backendenvios.dtos.ClientResponseDTO;
import com.couriersync.backendenvios.entities.Client;

public class ClientMapper {

    public static Client FromDtoToEntity(ClientRequestDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        return client;
    }

    public static ClientResponseDTO FromEntityToDto(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());
        return dto;
    }
}