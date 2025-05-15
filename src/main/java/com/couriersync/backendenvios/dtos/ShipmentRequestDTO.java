package com.couriersync.backendenvios.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Date;

@Data
public class ShipmentRequestDTO {

    @NotNull
    private Integer originAddressId;
    @NotNull
    private Integer destinationAddressId;
    @NotNull
    @Positive(message = "Weight must be a positive number")
    private Double weight;
    @NotNull
    private Integer priorityId;
    @NotNull
    private Integer clientId;
    @FutureOrPresent(message = "Shipping date cannot be in the past")
    private Date shippingDate;
    @Future(message = "Delivery date must be in the future")
    private Date deliveryDate;
}
