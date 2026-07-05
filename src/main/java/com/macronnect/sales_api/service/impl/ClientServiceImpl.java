package com.macronnect.sales_api.service.impl;

import com.macronnect.sales_api.exception.ClientNotFoundException;
import com.macronnect.sales_api.exception.EmailAlreadyExistsException;
import com.macronnect.sales_api.mapper.ClientMapper;
import com.macronnect.sales_api.model.dto.client.ClientDTO;
import com.macronnect.sales_api.model.dto.client.CreateClientRequest;
import com.macronnect.sales_api.model.dto.client.UpdateClientRequest;
import com.macronnect.sales_api.model.entity.Client;
import com.macronnect.sales_api.model.enums.Status;
import com.macronnect.sales_api.repository.ClientRepository;
import com.macronnect.sales_api.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;
    private final ClientMapper mapper;

    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private ClientDTO changeStatus(Long id, Status status) {

        Client client = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        client.setStatus(status);

        return mapper.toResponse(repository.save(client));
    }

    @Override
    public ClientDTO create(CreateClientRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        Client client = mapper.toEntity(request);

        Client saved = repository.save(client);

        return mapper.toResponse(saved);
    }

    @Override
    public ClientDTO update(Long id, UpdateClientRequest request) {
        Client client = repository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

        if (!client.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());

        repository.save(client);

        return mapper.toResponse(client);
    }

    @Override
    public ClientDTO getById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return mapper.toResponse(client);
    }

    @Override
    public Page<ClientDTO> getAll(Pageable pageable){

        return repository
                .findAllByStatus(Status.ACTIVE, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        changeStatus(id, Status.INACTIVE);
    }

    @Override
    public ClientDTO activate(Long id) {
        return changeStatus(id, Status.ACTIVE);
    }

}
