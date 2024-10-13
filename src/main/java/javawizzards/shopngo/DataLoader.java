package javawizzards.shopngo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DataLoader {

    @Autowired
    private ProductRepository productRepository;

    // This method is invoked automatically after Spring Boot starts
    @PostConstruct
    public void loadData() throws IOException {
        // Clear existing products to avoid duplication
        productRepository.deleteAll();

        ObjectMapper mapper = new ObjectMapper();

        // Load products from JSON file
        List<Product> products = mapper.readValue(
                new ClassPathResource("products.json").getInputStream(),
                new TypeReference<List<Product>>() {}
        );
        productRepository.saveAll(products);
    }
}
