package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> GetAllProducts();
    Product FindProductById(UUID id);
    List<Product> createProducts(List<ProductDto> productDtos);
    Product UpdateProduct(UUID id, ProductDto product);
    Product DeleteProduct(UUID id);
}
