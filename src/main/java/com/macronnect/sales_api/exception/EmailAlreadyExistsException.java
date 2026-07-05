package com.macronnect.sales_api.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("A client with email '" + email + "' already exists");
    }
}
