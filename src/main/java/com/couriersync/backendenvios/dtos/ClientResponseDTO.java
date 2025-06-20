package com.couriersync.backendenvios.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "clients")
public class ClientResponseDTO extends RepresentationModel<ClientResponseDTO> {
    private Integer id;
    private String name;
    private String email;
    private String phone;
}