package com.macronnect.sales_api.mapper;

import com.macronnect.sales_api.model.dto.product.CreateProductRequest;
import com.macronnect.sales_api.model.dto.product.ProductDTO;
import com.macronnect.sales_api.model.dto.product.UpdateProductRequest;
import com.macronnect.sales_api.model.entity.Product;
import com.macronnect.sales_api.model.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(CreateProductRequest request){

        Product product = new Product();

        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setStatus(Status.ACTIVE);

        return product;
    }

    public ProductDTO toResponse(Product product){

        return new ProductDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public void updateEntity(Product product, UpdateProductRequest request){

        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

    }

}
