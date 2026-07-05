package com.macronnect.sales_api.model.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDTO {
    private final Long id;
    private final String code;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ProductDTO(Long id, String code, String name, String description, BigDecimal price, Integer stock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
