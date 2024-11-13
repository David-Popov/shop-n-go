package javawizzards.shopngo.controllers;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Response;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.services.Product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_ReturnsProductsList() {
        Product product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setName("Test Product 1");
        product1.setDescription("Description");
        product1.setPrice(BigDecimal.valueOf(99.99));
        product1.setQuantity(10);
        product1.setImageUrl("image-url");
        product1.setIsDeleted(false);
        product1.setRating(4.5);

        ProductDto productDto1 = new ProductDto(
                "Test Product 1",
                "Description",
                BigDecimal.valueOf(99.99),
                10,
                "image-url",
                4.5
        );

        List<Product> products = Arrays.asList(product1);
        List<ProductDto> productDtos = Arrays.asList(productDto1);
        Page<ProductDto> productDtoPage = new PageImpl<>(productDtos);

        when(productService.getAllProducts(eq(0), eq(10), eq("name"), eq("ASC")))
                .thenReturn(productDtoPage);

        ResponseEntity<Response<?>> response = productController.getAllProducts(0, 10, "name", "ASC");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        @SuppressWarnings("unchecked")
        Page<ProductDto> resultProducts = (Page<ProductDto>) response.getBody().getData();
        assertEquals(1, resultProducts.getContent().size());
        assertEquals("Test Product 1", resultProducts.getContent().get(0).getName());
        assertEquals(BigDecimal.valueOf(99.99), resultProducts.getContent().get(0).getPrice());
        assertEquals("Description", resultProducts.getContent().get(0).getDescription());
        assertEquals(10, resultProducts.getContent().get(0).getQuantity());
        assertEquals("image-url", resultProducts.getContent().get(0).getImageUrl());
        assertEquals(4.5, resultProducts.getContent().get(0).getRating());
    }

    @Test
    void getAllProducts_HandlesEmptyList() {
        Page<ProductDto> emptyPage = new PageImpl<>(List.of());
        when(productService.getAllProducts(eq(0), eq(10), eq("name"), eq("ASC")))
                .thenReturn(emptyPage);

        ResponseEntity<Response<?>> response = productController.getAllProducts(0, 10, "name", "ASC");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        @SuppressWarnings("unchecked")
        Page<ProductDto> resultProducts = (Page<ProductDto>) response.getBody().getData();
        assertTrue(resultProducts.getContent().isEmpty());
    }

    @Test
    void getAllProducts_HandlesError() {
        when(productService.getAllProducts(eq(0), eq(10), eq("name"), eq("ASC")))
                .thenThrow(new RuntimeException("Test error"));

        ResponseEntity<Response<?>> response = productController.getAllProducts(0, 10, "name", "ASC");

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test error", response.getBody().getErrorDescription());
    }
}