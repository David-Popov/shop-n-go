package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.Create.CreateProductRequest;
import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Product.Update.UpdateProductDto;
import javawizzards.shopngo.entities.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> GetAllProducts();
    ProductDto FindProductById(long id);
    List<ProductDto> CreateProducts(CreateProductRequest product);
    void UpdateProduct(UpdateProductDto product);
    void DeleteProduct(long id);
}
