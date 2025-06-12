package com.couriersync.backendenvios.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public class ClientDataDTO {
    private Integer id;
    private ClientRequestDTO newClient;

    @AssertTrue(message = "Either client ID or new client data must be provided, but not both.")
    public boolean isValidClientData() {
        return (id != null && newClient == null) || (id == null && newClient != null);
    }

    public boolean hasId() {
        return id != null;
    }

    public boolean hasNewClientData() {
        return newClient != null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientRequestDTO getNewClient() {
        return newClient;
    }

    public void setNewClient(ClientRequestDTO newClient) {
        this.newClient = newClient;
    }
}