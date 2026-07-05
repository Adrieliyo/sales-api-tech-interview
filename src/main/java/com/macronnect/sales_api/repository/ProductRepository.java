package com.macronnect.sales_api.repository;

import com.macronnect.sales_api.model.entity.Product;
import com.macronnect.sales_api.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndStatus(Long id, Status status);

    Page<Product> findAllByStatus(Status status, Pageable pageable);

    Optional<Product> findByCode(String code);

}
