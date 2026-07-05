package com.macronnect.sales_api.exception.product;

import com.macronnect.sales_api.exception.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(Long id){
        super("Product with id " + id + " was not found.");
    }

}
