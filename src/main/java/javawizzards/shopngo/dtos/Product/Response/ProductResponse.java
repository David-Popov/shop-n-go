package javawizzards.shopngo.dtos.Product.Response;

import javawizzards.shopngo.dtos.Product.Request.ProductDto;
import javawizzards.shopngo.dtos.Response;

import java.time.LocalDateTime;

public class ProductResponse extends Response {
    ProductDto product;

    public ProductResponse(ProductDto product) {
        this.product = product;
    }

    public ProductResponse(LocalDateTime date, String errorDescription, String errorCode, String description, ProductDto product) {
        super(date, errorDescription, errorCode, description);
        this.product = product;
    }

    public ProductResponse(LocalDateTime date, String errorDescription, ProductDto product) {
        super(date, errorDescription);
        this.product = product;
    }

    public ProductResponse(LocalDateTime date, ProductDto product) {
        super(date);
        this.product = product;
    }

    public ProductDto getProducts() {
        return product;
    }

    public void setProducts(ProductDto products) {
        this.product = products;
    }
}
