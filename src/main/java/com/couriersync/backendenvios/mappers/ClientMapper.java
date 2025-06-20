package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.controllers.ClientController;
import com.couriersync.backendenvios.dtos.ClientRequestDTO;
import com.couriersync.backendenvios.dtos.ClientResponseDTO;
import com.couriersync.backendenvios.entities.Client;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
        dto.add(linkTo(methodOn(ClientController.class).getById(client.getId())).withSelfRel()); // Necesitarás un getById en ClientController
        dto.add(linkTo(methodOn(ClientController.class).getAllClients()).withRel("clients"));
        return dto;
    }

    public static List<ClientResponseDTO> FromEntityListToDtoList(List<Client> clients) {
        return clients.stream()
                .map(ClientMapper::FromEntityToDto)
                .collect(Collectors.toList());
    }
}