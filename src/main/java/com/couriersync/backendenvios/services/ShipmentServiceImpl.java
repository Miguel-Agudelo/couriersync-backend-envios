package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.ShipmentRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentResponseDTO;
import com.couriersync.backendenvios.entities.*;
import com.couriersync.backendenvios.mappers.ShipmentMapper;
import com.couriersync.backendenvios.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void updateShipment(Integer shipmentId, ShipmentRequestDTO dto) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        // Verificar que el envío no esté en estado "Entregado"
        if (shipment.getStatus().getName().equalsIgnoreCase("Entregado")) {
            throw new RuntimeException("Cannot edit shipment with finalized delivery status");
        }

        Address origin = addressRepository.findById(dto.getOriginAddressId())
                .orElseThrow(() -> new RuntimeException("Origin address not found"));

        Address destination = addressRepository.findById(dto.getDestinationAddressId())
                .orElseThrow(() -> new RuntimeException("Destination address not found"));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Priority priority = priorityRepository.findById(dto.getPriorityId())
                .orElseThrow(() -> new RuntimeException("Priority not found"));


        shipment.setOriginAddress(origin);
        shipment.setDestinationAddress(destination);
        shipment.setWeight(dto.getWeight());
        shipment.setPriority(priority);
        shipment.setClient(client);
        shipment.setShippingDate(dto.getShippingDate());
        shipment.setDeliveryDate(dto.getDeliveryDate());

        shipmentRepository.save(shipment);
    }

    @Override
    public ShipmentResponseDTO getShipmentById(Integer id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        if (shipment.getStatus() != null && "entregado".equalsIgnoreCase(shipment.getStatus().getName())) {
            throw new RuntimeException("El envío ya fue entregado y no se puede editar");
        }

        return ShipmentMapper.FromEntityToDto(shipment);
    }

    @Override
    public List<ShipmentResponseDTO> getAllShipments() {
        return shipmentRepository.findAll()
                .stream()
                .map(ShipmentMapper::FromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateShipmentStatusToInTransit(Integer shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        String currentStatus = shipment.getStatus() != null ? shipment.getStatus().getName() : "";

        if (currentStatus.equalsIgnoreCase("entregado")) {
            throw new RuntimeException("The status cannot be changed because the shipment has already been delivered.");
        }

        if (!currentStatus.equalsIgnoreCase("pendiente")) {
            throw new RuntimeException("The current status does not allow changing to 'En transito'.");
        }

        ShippingStatus inTransitStatus = shippingStatusRepository.findByName("en transito")
                .orElseThrow(() -> new RuntimeException("'En transito' status not found"));

        shipment.setStatus(inTransitStatus);
        shipment.setStatusUpdateDate(new Date()); // Fecha y hora del cambio de estado

        shipmentRepository.save(shipment);
    }


    @Override
    public void updateShipmentStatusToDelivered(Integer shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        String currentStatus = shipment.getStatus() != null ? shipment.getStatus().getName() : "";

        if (currentStatus.equalsIgnoreCase("entregado")) {
            throw new RuntimeException("The shipment has already been delivered.");
        }

        if (!currentStatus.equalsIgnoreCase("en transito")) {
            throw new RuntimeException("Only shipments in transit can be marked as delivered.");
        }

        ShippingStatus deliveredStatus = shippingStatusRepository.findByName("entregado")
                .orElseThrow(() -> new RuntimeException("'Entregado' status not found"));

        shipment.setStatus(deliveredStatus);
        shipment.setStatusUpdateDate(new Date()); // Fecha y hora de entrega

        shipmentRepository.save(shipment);
    }





}
