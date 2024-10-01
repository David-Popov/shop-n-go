package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.Create.CreateProductRequest;
import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.entities.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> GetAllProducts();
    Product GetProductById(int id);
    List<ProductDto> CreateProducts(CreateProductRequest product);
    void UpdateProduct(Product product);
    void DeleteProduct(int id);
}
