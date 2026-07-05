package com.macronnect.sales_api.validation;

import com.macronnect.sales_api.exception.product.ProductCodeAlreadyExistsException;
import com.macronnect.sales_api.exception.product.ProductNotFoundException;
import com.macronnect.sales_api.model.entity.Product;
import com.macronnect.sales_api.model.enums.Status;
import com.macronnect.sales_api.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductValidation {
    private final ProductRepository repository;

    public ProductValidation(ProductRepository repository) {
        this.repository = repository;
    }

    public Product validateProduct(Long id){

        return repository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() ->
                        new ProductNotFoundException(id));
    }

    /**
     * Validación para CREATE
     */
    public void validateCode(String code) {

        repository.findByCode(code)
                .ifPresent(product -> {
                    throw new ProductCodeAlreadyExistsException(code);
                });
    }

    /**
     * Validación para UPDATE
     */
    public void validateCode(String code, Long id) {

        repository.findByCode(code)
                .ifPresent(product -> {
                    if (!product.getId().equals(id)) {
                        throw new ProductCodeAlreadyExistsException(code);
                    }
                });
    }

}
