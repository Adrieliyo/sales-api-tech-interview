package com.macronnect.sales_api.exception.client;

import com.macronnect.sales_api.exception.ResourceNotFoundException;

public class ClientNotFoundException extends ResourceNotFoundException {

    public ClientNotFoundException(Long id) {
        super("Client with id " + id + " was not found");
    }
}
