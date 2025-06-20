package com.couriersync.backendenvios.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@Relation(collectionRelation = "shipments")
public class ShipmentResponseDTO extends RepresentationModel<ShipmentResponseDTO>{
    private Integer id;
    private String origin;
    private String destination;
    private String client;
    private Double weight;
    private String priority;
    private Date shippingDate;
    private Date deliveryDate;
    private Date registrationDate;
    private String status;
}