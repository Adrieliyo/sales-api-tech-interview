package com.macronnect.sales_api.mapper;

import com.macronnect.sales_api.model.dto.client.ClientDTO;
import com.macronnect.sales_api.model.dto.client.CreateClientRequest;
import com.macronnect.sales_api.model.entity.Client;
import com.macronnect.sales_api.model.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client toEntity(CreateClientRequest request) {
        Client client = new Client();

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());
        client.setStatus(Status.ACTIVE);

        return client;
    }

    public ClientDTO toResponse(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                client.getStatus(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }

}
