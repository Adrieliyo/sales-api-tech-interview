package com.macronnect.sales_api.exception.sale;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String productName, Integer availableStock) {
        super("Insufficient stock for product '" + productName + "'. Available stock: " + availableStock);
    }
}
