package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.controllers.AddressController;
import com.couriersync.backendenvios.controllers.ClientController;
import com.couriersync.backendenvios.controllers.ShipmentController;
import com.couriersync.backendenvios.dtos.ShipmentUpdateRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentResponseDTO;
import com.couriersync.backendenvios.entities.Address;
import com.couriersync.backendenvios.entities.Client;
import com.couriersync.backendenvios.entities.Priority;
import com.couriersync.backendenvios.entities.Shipment;
import com.couriersync.backendenvios.entities.ShippingStatus;
import com.couriersync.backendenvios.entities.User;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ShipmentMapper {

    public static Shipment FromDtoToEntity(ShipmentUpdateRequestDTO dto, Address origin, Address destination, Client client, Priority priority, User creator, ShippingStatus defaultStatus) {
        Shipment shipment = new Shipment();
        shipment.setRegistrationDate(new Date());
        shipment.setWeight(dto.getWeight());
        shipment.setOriginAddress(origin);
        shipment.setDestinationAddress(destination);
        shipment.setClient(client);
        shipment.setPriority(priority);
        shipment.setCreatedBy(creator);
        shipment.setStatus(defaultStatus);
        shipment.setShippingDate(Date.from(dto.getShippingDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        shipment.setDeliveryDate(Date.from(dto.getDeliveryDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        shipment.setStatusUpdateDate(new Date());
        return shipment;
    }

    public static ShipmentResponseDTO FromEntityToDto(Shipment shipment) {
        ShipmentResponseDTO dto = new ShipmentResponseDTO();
        dto.setId(shipment.getId());
        dto.setOrigin(shipment.getOriginAddress().getCity() + ", " + shipment.getOriginAddress().getAddress());
        dto.setDestination(shipment.getDestinationAddress().getCity() + ", " + shipment.getDestinationAddress().getAddress());
        dto.setClient(shipment.getClient().getName());
        dto.setWeight(shipment.getWeight());
        dto.setPriority(shipment.getPriority().getName());
        dto.setShippingDate(shipment.getShippingDate());
        dto.setDeliveryDate(shipment.getDeliveryDate());
        dto.setRegistrationDate(shipment.getRegistrationDate());
        dto.setStatus(shipment.getStatus().getName());

        dto.add(linkTo(methodOn(ShipmentController.class).getShipmentById(shipment.getId())).withSelfRel());
        dto.add(linkTo(methodOn(ShipmentController.class).getAllShipments()).withRel("shipments"));
        dto.add(linkTo(methodOn(ShipmentController.class).updateShipment(shipment.getId(), null)).withRel("update-shipment")); // Relación para actualizar
        dto.add(linkTo(methodOn(ShipmentController.class).updateStatusToInTransit(shipment.getId())).withRel("set-in-transit")); // Relación para cambiar a en transito
        dto.add(linkTo(methodOn(ShipmentController.class).updateShipmentToDelivered(shipment.getId())).withRel("set-delivered")); // Relación para cambiar a entregado
        dto.add(linkTo(methodOn(AddressController.class).getById(shipment.getOriginAddress().getId())).withRel("origin-address")); // Necesitarás getById en AddressController
        dto.add(linkTo(methodOn(AddressController.class).getById(shipment.getDestinationAddress().getId())).withRel("destination-address")); // Necesitarás getById en AddressController
        dto.add(linkTo(methodOn(ClientController.class).getById(shipment.getClient().getId())).withRel("client")); // Necesitarás getById en ClientController


        return dto;
    }

    public static List<ShipmentResponseDTO> FromEntityListToDtoList(List<Shipment> shipments) {
        return shipments.stream()
                .map(ShipmentMapper::FromEntityToDto)
                .collect(Collectors.toList());
    }
}