package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.ShipmentRequestDTO;
import com.couriersync.backendenvios.entities.*;
import com.couriersync.backendenvios.mappers.ShipmentMapper;
import com.couriersync.backendenvios.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShipmentServiceImpl implements ShipmentService{

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private ShippingStatusRepository shippingStatusRepository;
    @Autowired
    private UserRepository userRepository;


    public void createShipment(ShipmentRequestDTO dto, Integer userId) {
        try {
            Address origin = addressRepository.findById(dto.getOriginAddressId())
                    .orElseThrow(() -> new RuntimeException("Origin address not found"));

            Address destination = addressRepository.findById(dto.getDestinationAddressId())
                    .orElseThrow(() -> new RuntimeException("Destination address not found"));

            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            Priority priority = priorityRepository.findById(dto.getPriorityId())
                    .orElseThrow(() -> new RuntimeException("Priority not found"));

            User creator = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ShippingStatus status = shippingStatusRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Default shipping status not found"));

            Shipment shipment = ShipmentMapper.FromDtoToEntity(dto, origin, destination, client, priority, creator, status);

            shipmentRepository.save(shipment);
        } catch (RuntimeException e) {
            System.out.println("Error al crear envío: " + e.getMessage());
            throw e;
        }
    }
}
