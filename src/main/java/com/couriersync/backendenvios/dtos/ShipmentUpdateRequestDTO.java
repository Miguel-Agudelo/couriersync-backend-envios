package com.couriersync.backendenvios.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate; // ¡Cambio importante: usar LocalDate!


@Data
public class ShipmentUpdateRequestDTO {

    @NotNull(message = "Origin address ID is required")
    private Integer originAddressId;

    @NotNull(message = "Destination address ID is required")
    private Integer destinationAddressId;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    private Double weight;

    @NotNull(message = "Priority ID is required")
    private Integer priorityId;

    @NotNull(message = "Client ID is required")
    private Integer clientId;

    @NotNull(message = "Shipping date is required")
    @FutureOrPresent(message = "Shipping date must be in the present or future")
    private LocalDate shippingDate;

    @NotNull(message = "Delivery date is required")
    @FutureOrPresent(message = "Delivery date must be in the present or future") // También FutureOrPresent, ya que una entrega podría ser hoy mismo
    private LocalDate deliveryDate;


}