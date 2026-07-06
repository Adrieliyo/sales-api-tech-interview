package com.macronnect.sales_api.model.dto.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.macronnect.sales_api.model.dto.saleDetail.SaleDetailDTO;
import com.macronnect.sales_api.model.enums.SaleStatus;

public class SaleDTO {
    private final Long id;
    private final Long invoiceNumber;
    private final LocalDateTime date;
    private final SaleStatus status;
    private final BigDecimal total;
    private final Long clientId;
    private final String clientName;
    private final List<SaleDetailDTO> details;

    public SaleDTO(Long id, Long invoiceNumber, LocalDateTime date, SaleStatus status, BigDecimal total, Long clientId, String clientName, List<SaleDetailDTO> details) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.status = status;
        this.total = total;
        this.clientId = clientId;
        this.clientName = clientName;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public List<SaleDetailDTO> getDetails() {
        return details;
    }
}
