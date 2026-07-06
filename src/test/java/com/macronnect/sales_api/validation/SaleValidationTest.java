package com.macronnect.sales_api.validation;

import com.macronnect.sales_api.exception.client.ClientNotFoundException;
import com.macronnect.sales_api.exception.product.ProductNotFoundException;
import com.macronnect.sales_api.exception.sale.InsufficientStockException;
import com.macronnect.sales_api.model.entity.Client;
import com.macronnect.sales_api.model.entity.Product;
import com.macronnect.sales_api.model.enums.Status;
import com.macronnect.sales_api.repository.ClientRepository;
import com.macronnect.sales_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleValidationTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SaleValidation validation;

    @Test
    void shouldReturnClientWhenClientExists() {

        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findByIdAndStatus(1L, Status.ACTIVE))
                .thenReturn(Optional.of(client));

        Client result = validation.validateClient(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(clientRepository)
                .findByIdAndStatus(1L, Status.ACTIVE);

    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        when(clientRepository.findByIdAndStatus(1L, Status.ACTIVE))
                .thenReturn(Optional.empty());

        assertThrows(
                ClientNotFoundException.class,
                () -> validation.validateClient(1L)
        );

        verify(clientRepository)
                .findByIdAndStatus(1L, Status.ACTIVE);

    }

    @Test
    void shouldReturnProductWhenProductExists() {

        Product product = new Product();

        product.setId(10L);
        product.setStock(20);
        product.setPrice(new BigDecimal("15000"));

        when(productRepository.findByIdAndStatus(10L, Status.ACTIVE))
                .thenReturn(Optional.of(product));

        Product result = validation.validateProduct(10L);

        assertNotNull(result);

        assertEquals(10L, result.getId());

        verify(productRepository)
                .findByIdAndStatus(10L, Status.ACTIVE);

    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        when(productRepository.findByIdAndStatus(10L, Status.ACTIVE))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> validation.validateProduct(10L)
        );

        verify(productRepository)
                .findByIdAndStatus(10L, Status.ACTIVE);

    }

    @Test
    void shouldPassWhenStockIsEnough() {

        Product product = new Product();

        product.setStock(10);

        assertDoesNotThrow(() ->
                validation.validateStock(product, 5));

    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {

        Product product = new Product();

        product.setStock(3);

        assertThrows(
                InsufficientStockException.class,
                () -> validation.validateStock(product, 5)
        );

    }

}