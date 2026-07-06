package com.macronnect.sales_api.exception.sale;

import com.macronnect.sales_api.exception.ResourceNotFoundException;

public class SaleNotFoundException extends ResourceNotFoundException {
    public SaleNotFoundException(Long id) {
        super("Sale with id " + id + " was not found.");
    }
}
