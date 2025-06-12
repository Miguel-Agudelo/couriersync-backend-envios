package com.couriersync.backendenvios.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ShipmentCreationRequestDTO {

    @NotNull(message = "Origin address information is required")
    @Valid
    private AddressDataDTO originAddressInfo;

    @NotNull(message = "Destination address information is required")
    @Valid
    private AddressDataDTO destinationAddressInfo;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    private Double weight;

    @NotBlank(message = "Priority name is required")
    private String priorityName;

    @NotNull(message = "Client information is required")
    @Valid
    private ClientDataDTO clientInfo;

    @NotNull(message = "Shipping date is required")
    @FutureOrPresent(message = "Shipping date must be in the present or future")
    private LocalDate shippingDate;

    @NotNull(message = "Delivery date is required")
    @FutureOrPresent(message = "Delivery date must be in the present or future") // Considerar que puede ser hoy o en el futuro
    private LocalDate deliveryDate;

}