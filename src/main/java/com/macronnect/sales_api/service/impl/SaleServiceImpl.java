package com.macronnect.sales_api.service.impl;

import com.macronnect.sales_api.mapper.SaleMapper;
import com.macronnect.sales_api.model.dto.sale.CreateSaleRequest;
import com.macronnect.sales_api.model.dto.sale.SaleDTO;
import com.macronnect.sales_api.model.dto.saleDetail.CreateSaleDetailRequest;
import com.macronnect.sales_api.model.entity.Client;
import com.macronnect.sales_api.model.entity.Product;
import com.macronnect.sales_api.model.entity.Sale;
import com.macronnect.sales_api.model.entity.SaleDetail;
import com.macronnect.sales_api.model.enums.SaleStatus;
import com.macronnect.sales_api.repository.ProductRepository;
import com.macronnect.sales_api.repository.SaleRepository;
import com.macronnect.sales_api.service.SaleService;
import com.macronnect.sales_api.validation.SaleValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SaleMapper mapper;
    private final SaleValidation validation;

    public SaleServiceImpl(
            SaleRepository saleRepository,
            ProductRepository productRepository,
            SaleMapper mapper,
            SaleValidation validation) {

        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.validation = validation;
    }

    @Override
    @Transactional
    public SaleDTO create(CreateSaleRequest request) {

        Client client = validation.validateClient(request.getClientId());

        Sale sale = buildSale(client);

        List<SaleDetail> details = buildSaleDetails(sale, request.getDetails());

        sale.setDetails(details);

        sale.setTotal(calculateTotal(details));

        Sale saved = saleRepository.save(sale);

        return mapper.toResponse(saved);

    }

    @Override
    public SaleDTO getById(Long id){

        return mapper.toResponse(
                validation.validateSale(id));

    }

    @Override
    public Page<SaleDTO> getAll(Pageable pageable){

        return saleRepository
                .findAllByStatus(
                        SaleStatus.ACTIVE,
                        pageable)
                .map(mapper::toResponse);

    }

    @Override
    @Transactional
    public SaleDTO cancel(Long id){

        Sale sale =
                validation.validateSale(id);

        validation.validateSaleIsActive(sale);

        sale.setStatus(SaleStatus.CANCELLED);

        for(SaleDetail detail : sale.getDetails()){

            Product product = detail.getProduct();

            product.setStock(product.getStock() + detail.getQuantity());

            productRepository.save(product);

        }

        Sale saved =
                saleRepository.save(sale);

        return mapper.toResponse(saved);

    }

    private Sale buildSale(Client client) {

        Sale sale = new Sale();

        sale.setClient(client);
        sale.setInvoiceNumber(generateInvoiceNumber());
        sale.setDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.ACTIVE);

        return sale;

    }

    private SaleDetail buildSaleDetail(
            Sale sale,
            Product product,
            Integer quantity) {

        BigDecimal subtotal = product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));

        SaleDetail detail = new SaleDetail();

        detail.setSale(sale);
        detail.setProduct(product);
        detail.setQuantity(quantity);
        detail.setUnitPrice(product.getPrice());
        detail.setSubtotal(subtotal);

        return detail;
    }

    private List<SaleDetail> buildSaleDetails(
            Sale sale,
            List<CreateSaleDetailRequest> requests) {

        List<SaleDetail> details = new ArrayList<>();

        for (CreateSaleDetailRequest request : requests) {

            Product product = validation.validateProduct(request.getProductId());

            validation.validateStock(product, request.getQuantity());

            SaleDetail detail = buildSaleDetail(
                    sale,
                    product,
                    request.getQuantity());

            details.add(detail);

            discountStock(product, request.getQuantity());

        }

        return details;

    }

    private BigDecimal calculateTotal(List<SaleDetail> details) {

        return details.stream()
                .map(SaleDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private void discountStock(Product product, Integer quantity) {

        product.setStock(product.getStock() - quantity);

        productRepository.save(product);

    }

    private Long generateInvoiceNumber() {

        Long lastInvoice = saleRepository.findMaxInvoiceNumber();

        return lastInvoice == null
                ? 1L
                : lastInvoice + 1;
    }

}