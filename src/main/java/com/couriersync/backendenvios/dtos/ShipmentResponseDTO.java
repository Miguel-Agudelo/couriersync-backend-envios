package com.couriersync.backendenvios.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ShipmentResponseDTO {
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