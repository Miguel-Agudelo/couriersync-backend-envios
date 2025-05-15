package com.couriersync.backendenvios.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shipment")
    private Integer id;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "shipping_date")
    private Date shippingDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "id_origin_address", nullable = false)
    private Address originAddress;

    @ManyToOne
    @JoinColumn(name = "id_destination_address", nullable = false)
    private Address destinationAddress;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private ShippingStatus status;

    @ManyToOne
    @JoinColumn(name = "id_priority")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id_user", nullable = false)
    private User createdBy;

    @Column(name = "status_update_date")
    private Date statusUpdateDate;

}
