package com.macronnect.sales_api.service;

import com.macronnect.sales_api.model.dto.client.ClientDTO;
import com.macronnect.sales_api.model.dto.client.CreateClientRequest;
import com.macronnect.sales_api.model.dto.client.UpdateClientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    ClientDTO create(CreateClientRequest request);

    ClientDTO update(Long id, UpdateClientRequest request);

    ClientDTO getById(Long id);

    Page<ClientDTO> getAll(Pageable pageable);

    void delete(Long id);

    ClientDTO activate(Long id);
}
