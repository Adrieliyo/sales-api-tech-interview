package com.macronnect.sales_api.repository;

import com.macronnect.sales_api.model.entity.Sale;
import com.macronnect.sales_api.model.enums.SaleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findAllByStatus(SaleStatus status, Pageable pageable);

    @Query("SELECT MAX(s.invoiceNumber) FROM Sale s")
    Long findMaxInvoiceNumber();
}
