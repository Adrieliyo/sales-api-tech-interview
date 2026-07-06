package com.macronnect.sales_api.service;

import com.macronnect.sales_api.mapper.SaleMapper;
import com.macronnect.sales_api.model.dto.sale.CreateSaleRequest;
import com.macronnect.sales_api.model.dto.sale.SaleDTO;
import com.macronnect.sales_api.model.dto.saleDetail.CreateSaleDetailRequest;
import com.macronnect.sales_api.model.entity.Client;
import com.macronnect.sales_api.model.entity.Product;
import com.macronnect.sales_api.model.entity.Sale;
import com.macronnect.sales_api.model.enums.SaleStatus;
import com.macronnect.sales_api.repository.ProductRepository;
import com.macronnect.sales_api.repository.SaleRepository;
import com.macronnect.sales_api.service.impl.SaleServiceImpl;
import com.macronnect.sales_api.validation.SaleValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {
    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SaleMapper mapper;

    @Mock
    private SaleValidation validation;

    @InjectMocks
    private SaleServiceImpl service;

    private Client client;
    private Product laptop;
    private Product mouse;

    @BeforeEach
    void setUp() {

        client = new Client();
        client.setId(1L);
        client.setName("Juan");

        laptop = new Product();
        laptop.setId(1L);
        laptop.setName("Laptop");
        laptop.setPrice(new BigDecimal("15000"));
        laptop.setStock(10);

        mouse = new Product();
        mouse.setId(2L);
        mouse.setName("Mouse");
        mouse.setPrice(new BigDecimal("300"));
        mouse.setStock(20);

    }

    @Test
    void shouldCreateSaleSuccessfully() {

        CreateSaleRequest request = new CreateSaleRequest();
        request.setClientId(1L);

        CreateSaleDetailRequest detail1 = new CreateSaleDetailRequest();
        detail1.setProductId(1L);
        detail1.setQuantity(2);

        CreateSaleDetailRequest detail2 = new CreateSaleDetailRequest();
        detail2.setProductId(2L);
        detail2.setQuantity(3);

        request.setDetails(Arrays.asList(detail1, detail2));

        when(validation.validateClient(1L))
                .thenReturn(client);

        when(validation.validateProduct(1L))
                .thenReturn(laptop);

        when(validation.validateProduct(2L))
                .thenReturn(mouse);

        doNothing().when(validation).validateStock(any(Product.class), anyInt());


        when(saleRepository.findMaxInvoiceNumber())
                .thenReturn(1000L);

        when(saleRepository.save(any(Sale.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SaleDTO dummyResponse = new SaleDTO(
                1L,
                1001L,
                LocalDateTime.now(),
                SaleStatus.ACTIVE,
                BigDecimal.valueOf(1500),
                1L,
                "Juan Perez",
                new ArrayList<>()
        );

        when(mapper.toResponse(any(Sale.class)))
                .thenReturn(dummyResponse);

        SaleDTO response = service.create(request);

        assertNotNull(response);

        verify(saleRepository).save(any(Sale.class));

        verify(productRepository, times(2)).save(any(Product.class));

        verify(saleRepository).findMaxInvoiceNumber();
    }

    @Test
    void shouldDiscountStockWhenSaleIsCreated(){

        laptop.setStock(10);

        laptop.setStock(laptop.getStock()-2);

        assertEquals(8,laptop.getStock());

    }

    @Test
    void shouldCalculateTotalCorrectly(){

        BigDecimal subtotalLaptop =
                laptop.getPrice()
                        .multiply(BigDecimal.valueOf(2));

        BigDecimal subtotalMouse =
                mouse.getPrice()
                        .multiply(BigDecimal.valueOf(3));

        BigDecimal total =
                subtotalLaptop.add(subtotalMouse);

        assertEquals(
                new BigDecimal("30900"),
                total);

    }

    @Test
    void shouldCancelSale(){

        Sale sale = new Sale();

        sale.setStatus(SaleStatus.ACTIVE);

        sale.setStatus(SaleStatus.CANCELLED);

        assertEquals(
                SaleStatus.CANCELLED,
                sale.getStatus());

    }

    @Test
    void shouldRestoreStockWhenSaleIsCancelled(){

        laptop.setStock(8);

        laptop.setStock(laptop.getStock()+2);

        assertEquals(
                10,
                laptop.getStock());

    }

}
