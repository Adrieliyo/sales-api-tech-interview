package com.macronnect.sales_api.validation;

import com.macronnect.sales_api.exception.client.ClientNotFoundException;
import com.macronnect.sales_api.exception.product.ProductNotFoundException;
import com.macronnect.sales_api.exception.sale.InsufficientStockException;
import com.macronnect.sales_api.exception.sale.SaleAlreadyCancelledException;
import com.macronnect.sales_api.exception.sale.SaleNotFoundException;
import com.macronnect.sales_api.model.entity.Client;
import com.macronnect.sales_api.model.entity.Product;
import com.macronnect.sales_api.model.entity.Sale;
import com.macronnect.sales_api.model.enums.SaleStatus;
import com.macronnect.sales_api.model.enums.Status;
import com.macronnect.sales_api.repository.ClientRepository;
import com.macronnect.sales_api.repository.ProductRepository;
import com.macronnect.sales_api.repository.SaleRepository;
import com.macronnect.sales_api.service.SaleService;
import org.springframework.stereotype.Component;

@Component
public class SaleValidation {
    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public SaleValidation(SaleRepository saleRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public Sale validateSale(Long id) {

        return saleRepository.findById(id)
                .orElseThrow(() ->
                        new SaleNotFoundException(id));

    }

    public Client validateClient(Long id) {

        return clientRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() ->
                        new ClientNotFoundException(id));

    }

    public Product validateProduct(Long id) {

        return productRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() ->
                        new ProductNotFoundException(id));

    }

    public void validateStock(Product product, Integer quantity) {

        if (product.getStock() < quantity) {

            throw new InsufficientStockException(
                    product.getName(),
                    product.getStock());

        }

    }

    public void validateSaleIsActive(Sale sale) {

        if (sale.getStatus() == SaleStatus.CANCELLED) {

            throw new SaleAlreadyCancelledException(
                    sale.getId());

        }

    }
}
