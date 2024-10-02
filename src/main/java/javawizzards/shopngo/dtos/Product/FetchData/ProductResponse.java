package javawizzards.shopngo.dtos.Product.FetchData;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Response;

import java.time.LocalDateTime;

public class ProductResponse extends Response {
    ProductDto product;

    public ProductResponse(ProductDto products) {
        this.product = products;
    }

    public ProductResponse(String errorDesciption) {
        setErrorDescription(errorDesciption);
    }

    public ProductResponse(String description, ProductDto product) {
        setDescription(description);
        setProducts(product);
    }

    public ProductResponse(LocalDateTime date, String errorDescription, String errorCode, String description, ProductDto product) {
        super(date, errorDescription, errorCode, description);
        this.product = product;
    }

    public ProductDto getProducts() {
        return product;
    }

    public void setProducts(ProductDto products) {
        this.product = products;
    }
}
