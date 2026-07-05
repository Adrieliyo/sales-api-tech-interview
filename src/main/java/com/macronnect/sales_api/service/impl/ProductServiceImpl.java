package com.macronnect.sales_api.service.impl;

import com.macronnect.sales_api.exception.product.ProductNotFoundException;
import com.macronnect.sales_api.mapper.ProductMapper;
import com.macronnect.sales_api.model.dto.product.CreateProductRequest;
import com.macronnect.sales_api.model.dto.product.ProductDTO;
import com.macronnect.sales_api.model.dto.product.UpdateProductRequest;
import com.macronnect.sales_api.model.entity.Product;
import com.macronnect.sales_api.model.enums.Status;
import com.macronnect.sales_api.repository.ProductRepository;
import com.macronnect.sales_api.service.ProductService;
import com.macronnect.sales_api.validation.ProductValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ProductValidation validation;

    public ProductServiceImpl(ProductRepository repository,
                              ProductMapper mapper,
                              ProductValidation validation){

        this.repository = repository;
        this.mapper = mapper;
        this.validation = validation;
    }

    @Override
    public ProductDTO create(CreateProductRequest request){

        validation.validateCode(request.getCode());

        Product product = mapper.toEntity(request);

        Product saved = repository.save(product);

        return mapper.toResponse(saved);

    }

    @Override
    public ProductDTO update(Long id, UpdateProductRequest request){

        Product product = validation.validateProduct(id);

        validation.validateCode(request.getCode(), id);

        mapper.updateEntity(product, request);

        return mapper.toResponse(repository.save(product));

    }

    @Override
    public ProductDTO getById(Long id){

        return mapper.toResponse(
                validation.validateProduct(id));

    }

    @Override
    public Page<ProductDTO> getAll(Pageable pageable){

        return repository.findAllByStatus(Status.ACTIVE,pageable)
                .map(mapper::toResponse);

    }

    @Override
    public void delete(Long id){

        Product product = validation.validateProduct(id);

        product.setStatus(Status.INACTIVE);

        repository.save(product);

    }

    @Override
    public ProductDTO activate(Long id){

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id));

        product.setStatus(Status.ACTIVE);

        repository.save(product);

        return mapper.toResponse(product);

    }
}
