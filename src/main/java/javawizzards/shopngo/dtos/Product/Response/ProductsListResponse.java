package javawizzards.shopngo.dtos.Product.Response;

import javawizzards.shopngo.dtos.Product.Request.ProductDto;
import javawizzards.shopngo.dtos.Response;

import java.time.LocalDateTime;
import java.util.List;

public class ProductsListResponse extends Response{
    List<ProductDto> products;

    public ProductsListResponse(List<ProductDto> products) {
        this.products = products;
    }

    public ProductsListResponse(String errorDesciption) {
        setErrorDescription(errorDesciption);
    }

    public ProductsListResponse(LocalDateTime date, String errorDescription, String errorCode, String description, List<ProductDto> products) {
        super(date, errorDescription, errorCode, description);
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
