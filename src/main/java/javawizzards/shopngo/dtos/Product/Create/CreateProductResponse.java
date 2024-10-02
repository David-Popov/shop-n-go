package javawizzards.shopngo.dtos.Product.Create;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Response;

import java.time.LocalDateTime;
import java.util.List;

public class CreateProductResponse extends Response {
    List<ProductDto> products;

    public CreateProductResponse(List<ProductDto> products) {
        this.products = products;
    }

    public CreateProductResponse(String errorDesciption) {
        setErrorDescription(errorDesciption);
    }

    public CreateProductResponse(LocalDateTime date, String errorDescription, String errorCode, String description, List<ProductDto> products) {
        super(date, errorDescription, errorCode, description);
        setProducts(products);
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
