package com.macronnect.sales_api.service;

import com.macronnect.sales_api.model.dto.product.CreateProductRequest;
import com.macronnect.sales_api.model.dto.product.ProductDTO;
import com.macronnect.sales_api.model.dto.product.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDTO create(CreateProductRequest request);

    ProductDTO update(Long id, UpdateProductRequest request);

    ProductDTO getById(Long id);

    Page<ProductDTO> getAll(Pageable pageable);

    void delete(Long id);

    ProductDTO activate(Long id);
}
