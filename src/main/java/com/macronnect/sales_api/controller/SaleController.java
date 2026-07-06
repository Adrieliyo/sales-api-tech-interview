package com.macronnect.sales_api.controller;

import com.macronnect.sales_api.model.dto.sale.CreateSaleRequest;
import com.macronnect.sales_api.model.dto.sale.SaleDTO;
import com.macronnect.sales_api.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales", description = "Operations related to sales management")
public class SaleController {
    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @Operation(
            summary = "Create a new sale",
            description = "Registers a new sale associated with an existing client. The server validates stock availability, calculates totals and generates the invoice number."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sale created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Client or product not found")
    })
    @PostMapping
    public ResponseEntity<SaleDTO> create(
            @Valid @RequestBody CreateSaleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));

    }

    @Operation(
            summary = "Get sale by ID",
            description = "Returns a sale including all its detail lines."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale found"),
            @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id));

    }

    @Operation(
            summary = "Get all sales",
            description = "Returns a paginated list of active sales."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sales retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<SaleDTO>> getAll(
            Pageable pageable) {

        return ResponseEntity.ok(
                service.getAll(pageable));

    }

    @Operation(
            summary = "Cancel a sale",
            description = "Cancels a sale and restores the stock of all products involved."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
            @ApiResponse(responseCode = "409", description = "Sale already cancelled")
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<SaleDTO> cancel(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.cancel(id));

    }
}
