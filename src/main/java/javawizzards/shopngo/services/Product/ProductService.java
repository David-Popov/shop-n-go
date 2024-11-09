package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.entities.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    // Existing methods
    List<Product> GetAllProducts();
    Product FindProductById(UUID id);
    List<Product> createProducts(List<ProductDto> productDtos);
    Product UpdateProduct(UUID id, ProductDto product);
    Product DeleteProduct(UUID id);

    // New methods
    Page<ProductDto> getAllProducts(int page, int size, String sortBy, String direction);
    Page<ProductDto> searchProducts(String searchTerm, int page, int size);
    List<ProductDto> findProductsByPriceRangeAndRating(BigDecimal minPrice, BigDecimal maxPrice, double minRating);
    List<ProductDto> findLowStockProducts(int threshold);
    Page<ProductDto> findByCategories(List<String> categories, int page, int size);
}