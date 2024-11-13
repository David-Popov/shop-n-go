package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_ReturnsFilteredProducts() {
        Product product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setName("Test Product");
        product1.setPrice(BigDecimal.valueOf(99.99));
        product1.setIsDeleted(false);

        Product product2 = new Product();
        product2.setId(UUID.randomUUID());
        product2.setName("Deleted Product");
        product2.setPrice(BigDecimal.valueOf(149.99));
        product2.setIsDeleted(true);

        List<Product> allProducts = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(allProducts);

        List<Product> result = productService.GetAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
        assertTrue(result.stream().noneMatch(Product::getIsDeleted));
    }

    @Test
    void getAllProducts_WithPagination_ReturnsPagedProducts() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setIsDeleted(false);

        ProductDto productDto = new ProductDto(
                "Test Product",
                "Description",
                BigDecimal.valueOf(99.99),
                10,
                "image-url",
                4.5
        );

        Page<Product> productPage = new PageImpl<>(List.of(product));
        when(productRepository.findByIsDeletedFalse(any(PageRequest.class))).thenReturn(productPage);
        when(productMapper.toProductDto(any(Product.class))).thenReturn(productDto);

        Page<ProductDto> result = productService.getAllProducts(0, 10, "price", "DESC");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Product", result.getContent().get(0).getName());
    }

    @Test
    void createProducts_ReturnsCreatedProducts() {
        ProductDto productDto = new ProductDto(
                "Test Product",
                "Description",
                BigDecimal.valueOf(99.99),
                10,
                "image-url",
                4.5
        );

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(99.99));

        when(productMapper.toProductFromProductDto(any(ProductDto.class))).thenReturn(product);
        when(productRepository.saveAll(any())).thenReturn(List.of(product));

        List<Product> result = productService.createProducts(List.of(productDto));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
    }
}