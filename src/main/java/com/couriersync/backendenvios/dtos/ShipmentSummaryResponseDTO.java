package com.couriersync.backendenvios.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentSummaryResponseDTO {
    private Long pending;
    private Long inTransit;
    private Long delivered;
    private Long delayed;
}