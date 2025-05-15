package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.dtos.ShipmentRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentResponseDTO;
import com.couriersync.backendenvios.entities.*;

import java.util.Date;

public class ShipmentMapper {

    public static Shipment FromDtoToEntity(ShipmentRequestDTO dto, Address origin, Address destination, Client client, Priority priority, User creator, ShippingStatus defaultStatus) {
        Shipment shipment = new Shipment();
        shipment.setRegistrationDate(new Date());
        shipment.setWeight(dto.getWeight());
        shipment.setOriginAddress(origin);
        shipment.setDestinationAddress(destination);
        shipment.setClient(client);
        shipment.setPriority(priority);
        shipment.setCreatedBy(creator);
        shipment.setStatus(defaultStatus);
        shipment.setShippingDate(dto.getShippingDate());
        shipment.setDeliveryDate(dto.getDeliveryDate());
        return shipment;
    }

    public static ShipmentResponseDTO FromEntityToDto(Shipment shipment) {
        ShipmentResponseDTO dto = new ShipmentResponseDTO();
        dto.setId(shipment.getId());
        dto.setOrigin(shipment.getOriginAddress().toString());
        dto.setDestination(shipment.getDestinationAddress().toString());
        dto.setClient(shipment.getClient().getName());
        dto.setWeight(shipment.getWeight());
        dto.setPriority(shipment.getPriority().getName());
        dto.setShippingDate(shipment.getShippingDate());
        dto.setDeliveryDate(shipment.getDeliveryDate());
        dto.setRegistrationDate(shipment.getRegistrationDate());
        dto.setStatus(shipment.getStatus().getName());
        return dto;
    }

}
