package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.AddressDataDTO;
import com.couriersync.backendenvios.dtos.AddressResponseDTO;
import com.couriersync.backendenvios.dtos.ClientDataDTO;
import com.couriersync.backendenvios.dtos.ClientResponseDTO;
import com.couriersync.backendenvios.dtos.ShipmentCreationRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentResponseDTO;
import com.couriersync.backendenvios.dtos.ShipmentSummaryResponseDTO;
import com.couriersync.backendenvios.dtos.ShipmentUpdateRequestDTO;
import com.couriersync.backendenvios.entities.Address;
import com.couriersync.backendenvios.entities.Client;
import com.couriersync.backendenvios.entities.Priority;
import com.couriersync.backendenvios.entities.Shipment;
import com.couriersync.backendenvios.entities.ShippingStatus;
import com.couriersync.backendenvios.entities.User;
import com.couriersync.backendenvios.mappers.ShipmentMapper;
import com.couriersync.backendenvios.repositories.AddressRepository;
import com.couriersync.backendenvios.repositories.ClientRepository;
import com.couriersync.backendenvios.repositories.PriorityRepository;
import com.couriersync.backendenvios.repositories.ShipmentRepository;
import com.couriersync.backendenvios.repositories.ShippingStatusRepository;
import com.couriersync.backendenvios.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentServiceImpl implements ShipmentService {

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
    @Autowired
    private AddressService addressService;
    @Autowired
    private ClientService clientService;

    @Override
    @Transactional
    public void createShipment(ShipmentCreationRequestDTO dto, Integer userId) {
        try {
            Address originAddress = processAddressData(dto.getOriginAddressInfo());
            Address destinationAddress = processAddressData(dto.getDestinationAddressInfo());
            Client client = processClientData(dto.getClientInfo());

            Priority priority = priorityRepository.findByName(dto.getPriorityName().toUpperCase())
                    .orElseThrow(() -> new RuntimeException("Priority '" + dto.getPriorityName() + "' not found. Valid options: ALTA, MEDIA, BAJA."));

            ShippingStatus initialStatus = shippingStatusRepository.findByName("pendiente")
                    .orElseThrow(() -> new RuntimeException("Initial status 'pendiente' not found in database. Please ensure it exists."));

            User creator = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found."));

            Shipment shipment = new Shipment();
            shipment.setRegistrationDate(new Date());
            shipment.setWeight(dto.getWeight());
            shipment.setShippingDate(Date.from(dto.getShippingDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            shipment.setDeliveryDate(Date.from(dto.getDeliveryDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            shipment.setOriginAddress(originAddress);
            shipment.setDestinationAddress(destinationAddress);
            shipment.setClient(client);
            shipment.setPriority(priority);
            shipment.setStatus(initialStatus);
            shipment.setCreatedBy(creator);
            shipment.setStatusUpdateDate(new Date());

            shipmentRepository.save(shipment);

        } catch (RuntimeException e) {
            System.err.println("Error al crear envío: " + e.getMessage());
            throw e;
        }
    }

    private Address processAddressData(AddressDataDTO addressData) {
        if (addressData.hasId()) {
            return addressRepository.findById(addressData.getId())
                    .orElseThrow(() -> new RuntimeException("Address with ID " + addressData.getId() + " not found."));
        } else if (addressData.hasNewAddressData()) {
            AddressResponseDTO newAddressDto = addressService.createAddress(addressData.getNewAddress());
            return addressRepository.findById(newAddressDto.getId())
                    .orElseThrow(() -> new RuntimeException("Newly created address with ID " + newAddressDto.getId() + " not found after creation."));
        }
        throw new IllegalArgumentException("Address data is invalid: either ID or new address data must be provided.");
    }

    private Client processClientData(ClientDataDTO clientData) {
        if (clientData.hasId()) {
            return clientRepository.findById(clientData.getId())
                    .orElseThrow(() -> new RuntimeException("Client with ID " + clientData.getId() + " not found."));
        } else if (clientData.hasNewClientData()) {
            ClientResponseDTO newClientDto = clientService.createClient(clientData.getNewClient());
            return clientRepository.findById(newClientDto.getId())
                    .orElseThrow(() -> new RuntimeException("Newly created client with ID " + newClientDto.getId() + " not found after creation."));
        }
        throw new IllegalArgumentException("Client data is invalid: either ID or new client data must be provided.");
    }

    @Override
    public void updateShipment(Integer shipmentId, ShipmentUpdateRequestDTO dto) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

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
        shipment.setShippingDate(Date.from(dto.getShippingDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        shipment.setDeliveryDate(Date.from(dto.getDeliveryDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        shipmentRepository.save(shipment);
    }

    @Override
    public ShipmentResponseDTO getShipmentById(Integer id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
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
                .orElseThrow(() -> new RuntimeException("'En transito' status not found in database. Please ensure it exists."));

        shipment.setStatus(inTransitStatus);
        shipment.setStatusUpdateDate(new Date());

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
                .orElseThrow(() -> new RuntimeException("'Entregado' status not found in database. Please ensure it exists."));

        shipment.setStatus(deliveredStatus);
        shipment.setStatusUpdateDate(new Date());

        shipmentRepository.save(shipment);
    }

    @Override
    public ShipmentSummaryResponseDTO getShipmentSummaryForAdmin() {
        Long pending = shipmentRepository.countByStatus_Name("pendiente");
        Long inTransit = shipmentRepository.countByStatus_Name("en transito");
        Long delivered = shipmentRepository.countByStatus_Name("entregado");
        Long delayed = shipmentRepository.countDelayedShipments();

        return new ShipmentSummaryResponseDTO(pending, inTransit, delivered, delayed);
    }
}