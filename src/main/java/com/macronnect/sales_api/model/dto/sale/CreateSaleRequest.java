package com.macronnect.sales_api.model.dto.sale;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.macronnect.sales_api.model.dto.saleDetail.CreateSaleDetailRequest;

public class CreateSaleRequest {
    @NotNull
    private Long clientId;

    @Valid
    @NotEmpty
    private List<CreateSaleDetailRequest> details;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<CreateSaleDetailRequest> getDetails() {
        return details;
    }

    public void setDetails(List<CreateSaleDetailRequest> details) {
        this.details = details;
    }
}
