package com.macronnect.sales_api.service;

import com.macronnect.sales_api.model.dto.sale.CreateSaleRequest;
import com.macronnect.sales_api.model.dto.sale.SaleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {
    SaleDTO create(CreateSaleRequest request);

    SaleDTO getById(Long id);

    Page<SaleDTO> getAll(Pageable pageable);

    SaleDTO cancel(Long id);
}
