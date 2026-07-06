package com.macronnect.sales_api.mapper;

import com.macronnect.sales_api.model.dto.sale.SaleDTO;
import com.macronnect.sales_api.model.dto.saleDetail.SaleDetailDTO;
import com.macronnect.sales_api.model.entity.Sale;
import com.macronnect.sales_api.model.entity.SaleDetail;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SaleMapper {
    public SaleDTO toResponse(Sale sale) {

        return new SaleDTO(
                sale.getId(),
                sale.getInvoiceNumber(),
                sale.getDate(),
                sale.getStatus(),
                sale.getTotal(),
                sale.getClient().getId(),
                sale.getClient().getName(),
                sale.getDetails()
                        .stream()
                        .map(this::toDetailResponse)
                        .collect(Collectors.toList())
        );

    }

    private SaleDetailDTO toDetailResponse(SaleDetail detail) {

        return new SaleDetailDTO(
                detail.getId(),
                detail.getProduct().getId(),
                detail.getProduct().getCode(),
                detail.getProduct().getName(),
                detail.getQuantity(),
                detail.getUnitPrice(),
                detail.getSubtotal()
        );

    }
}
