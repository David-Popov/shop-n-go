package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.Request.ProductDto;
import javawizzards.shopngo.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> GetAllProducts();
    Product FindProductById(long id);
    List<Product> createProducts(List<ProductDto> productDtos);
    Product UpdateProduct(long id, ProductDto product);
    Product DeleteProduct(long id);
}
