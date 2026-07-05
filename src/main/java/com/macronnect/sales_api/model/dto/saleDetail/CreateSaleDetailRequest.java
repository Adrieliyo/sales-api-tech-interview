package com.macronnect.sales_api.model.dto.saleDetail;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateSaleDetailRequest {
    @NotNull
    private Long productId;

    @Min(1)
    private Integer quantity;

    public CreateSaleDetailRequest() {}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
