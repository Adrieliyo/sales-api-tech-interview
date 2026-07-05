package com.macronnect.sales_api.controller;

import com.macronnect.sales_api.model.dto.product.CreateProductRequest;
import com.macronnect.sales_api.model.dto.product.ProductDTO;
import com.macronnect.sales_api.model.dto.product.UpdateProductRequest;
import com.macronnect.sales_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Operations related to product management")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @Operation(
            summary = "Create a new product",
            description = "Creates a new product if the product code is not already registered."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Product code already exists")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> create(
            @Valid @RequestBody CreateProductRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));

    }


    @Operation(
            summary = "Update product",
            description = "Updates an existing product's information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "409", description = "Product code already exists")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request){

        return ResponseEntity.ok(service.update(id,request));

    }


    @Operation(
            summary = "Get all products",
            description = "Returns a paginated list of active products."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAll(
            Pageable pageable){

        return ResponseEntity.ok(service.getAll(pageable));

    }


    @Operation(
            summary = "Get product by ID",
            description = "Returns a product by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(
            @PathVariable Long id){

        return ResponseEntity.ok(service.getById(id));

    }


    @Operation(
            summary = "Reactivate product",
            description = "Changes the product's status from INACTIVE to ACTIVE."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product reactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProductDTO> activate(
            @PathVariable Long id){

        return ResponseEntity.ok(service.activate(id));

    }


    @Operation(
            summary = "Deactivate product",
            description = "Performs a logical deletion by changing the product's status to INACTIVE."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id){

        service.delete(id);

        return ResponseEntity.noContent().build();

    }
}
