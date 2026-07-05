package com.macronnect.sales_api.exception.product;

import com.macronnect.sales_api.exception.DuplicateResourceException;

public class ProductCodeAlreadyExistsException extends DuplicateResourceException {

    public ProductCodeAlreadyExistsException(String code){
        super("Product code '" + code + "' already exists.");
    }

}
