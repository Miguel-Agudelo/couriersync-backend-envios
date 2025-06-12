package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.ClientRequestDTO;
import com.couriersync.backendenvios.dtos.ClientResponseDTO;
import com.couriersync.backendenvios.entities.Client;
import com.couriersync.backendenvios.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientResponseDTO createClient(ClientRequestDTO requestDTO) {
        // Validación para evitar duplicados por email
        if (clientRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Client with email " + requestDTO.getEmail() + " already exists.");
        }

        Client client = new Client();
        client.setName(requestDTO.getName());
        client.setEmail(requestDTO.getEmail());
        client.setPhone(requestDTO.getPhone());
        Client savedClient = clientRepository.save(client);
        return new ClientResponseDTO(savedClient.getId(), savedClient.getName(), savedClient.getEmail(), savedClient.getPhone());
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> new ClientResponseDTO(client.getId(), client.getName(), client.getEmail(), client.getPhone()))
                .collect(Collectors.toList());
    }
}