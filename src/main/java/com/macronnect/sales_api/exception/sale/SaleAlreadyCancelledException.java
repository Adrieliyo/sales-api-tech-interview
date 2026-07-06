package com.macronnect.sales_api.exception.sale;

public class SaleAlreadyCancelledException extends RuntimeException{
    public SaleAlreadyCancelledException(Long id) {
        super("Sale with id " + id + " is already cancelled.");
    }
}
