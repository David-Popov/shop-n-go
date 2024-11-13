package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByIsDeletedFalse_ReturnsPaginatedActiveProducts() {
        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(BigDecimal.valueOf(99.99));
        product1.setIsDeleted(false);
        product1.setQuantity(10);
        product1.setImageUrl("image-url-1");
        product1.setRating(4.5);

        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(BigDecimal.valueOf(149.99));
        product2.setIsDeleted(false);
        product2.setQuantity(15);
        product2.setImageUrl("image-url-2");
        product2.setRating(4.8);

        productRepository.save(product1);
        productRepository.save(product2);

        Page<Product> result = productRepository.findByIsDeletedFalse(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "price"))
        );

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(product1.getName(), result.getContent().get(0).getName());
        assertEquals(product2.getName(), result.getContent().get(1).getName());
    }

    @Test
    void findByIsDeletedFalse_ExcludesDeletedProducts() {
        Product product1 = new Product();
        product1.setName("Active Product");
        product1.setIsDeleted(false);
        product1.setPrice(BigDecimal.valueOf(99.99));

        Product product2 = new Product();
        product2.setName("Deleted Product");
        product2.setIsDeleted(true);
        product2.setPrice(BigDecimal.valueOf(149.99));

        productRepository.save(product1);
        productRepository.save(product2);

        Page<Product> result = productRepository.findByIsDeletedFalse(
                PageRequest.of(0, 10)
        );

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Active Product", result.getContent().get(0).getName());
    }
}