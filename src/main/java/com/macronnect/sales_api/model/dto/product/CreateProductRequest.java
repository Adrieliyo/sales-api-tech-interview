package com.macronnect.sales_api.model.dto.product;

import java.math.BigDecimal;

import javax.validation.constraints.*;

public class CreateProductRequest {
    @NotBlank(message = "Product code is required.")
    @Size(max = 50, message = "Product code must not exceed 50 characters.")
    private String code;

    @NotBlank(message = "Product name is required.")
    @Size(max = 255, message = "Product name must not exceed 255 characters.")
    private String name;

    @NotBlank(message = "Description is required.")
    @Size(max = 500)
    private String description;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "Stock is required.")
    @Min(value = 0, message = "Stock cannot be negative.")
    private Integer stock;

    public CreateProductRequest() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
