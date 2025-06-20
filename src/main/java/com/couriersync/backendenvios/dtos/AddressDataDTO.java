package com.couriersync.backendenvios.dtos;

import jakarta.validation.constraints.AssertTrue;

public class AddressDataDTO {
    private Integer id;
    private AddressRequestDTO newAddress;

    @AssertTrue(message = "Either address ID or new address data must be provided, but not both.")
    public boolean isValidAddressData() {
        return (id != null && newAddress == null) || (id == null && newAddress != null);
    }

    public boolean hasId() {
        return id != null;
    }

    public boolean hasNewAddressData() {
        return newAddress != null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AddressRequestDTO getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(AddressRequestDTO newAddress) {
        this.newAddress = newAddress;
    }
}