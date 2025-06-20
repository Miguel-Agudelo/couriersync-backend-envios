package com.couriersync.backendenvios.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "addresses")
public class AddressResponseDTO extends RepresentationModel<AddressResponseDTO>{
    private Integer id;
    private String city;
    private String address;
}