package com.macronnect.sales_api.exception.client;

import com.macronnect.sales_api.exception.DuplicateResourceException;

public class EmailAlreadyExistsException extends DuplicateResourceException {

    public EmailAlreadyExistsException(String email) {
        super("A client with email '" + email + "' already exists");
    }
}
