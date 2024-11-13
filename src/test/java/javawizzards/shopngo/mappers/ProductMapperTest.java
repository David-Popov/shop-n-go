package javawizzards.shopngo.mappers;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    void toProductDto_MapsAllFields() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setQuantity(10);
        product.setImageUrl("test-image.jpg");
        product.setRating(4.5);
        product.setIsDeleted(false);

        ProductDto result = productMapper.toProductDto(product);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getQuantity(), result.getQuantity());
        assertEquals(product.getImageUrl(), result.getImageUrl());
        assertEquals(product.getRating(), result.getRating());
    }

    @Test
    void toProductDtoList_MapsAllProducts() {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(BigDecimal.valueOf(99.99));
        product1.setQuantity(10);
        product1.setImageUrl("image-1.jpg");
        product1.setRating(4.5);
        product1.setIsDeleted(false);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(BigDecimal.valueOf(149.99));
        product2.setQuantity(15);
        product2.setImageUrl("image-2.jpg");
        product2.setRating(4.8);
        product2.setIsDeleted(false);

        List<Product> products = Arrays.asList(product1, product2);

        List<ProductDto> result = productMapper.toProductDtoList(products);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1.getName(), result.get(0).getName());
        assertEquals(product2.getName(), result.get(1).getName());
        assertEquals(product1.getPrice(), result.get(0).getPrice());
        assertEquals(product2.getPrice(), result.get(1).getPrice());
    }

    @Test
    void toProductFromProductDto_MapsAllFields() {
        ProductDto dto = new ProductDto(
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(99.99),
                10,
                "test-image.jpg",
                4.5
        );

        Product result = productMapper.toProductFromProductDto(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getPrice(), result.getPrice());
        assertEquals(dto.getQuantity(), result.getQuantity());
        assertEquals(dto.getImageUrl(), result.getImageUrl());
        assertEquals(dto.getRating(), result.getRating());
    }
}