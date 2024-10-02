package javawizzards.shopngo.dtos.Product.FetchData;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Response;

import java.time.LocalDateTime;
import java.util.List;

public class ListOfProductsResponse extends Response{
    List<ProductDto> products;

    public ListOfProductsResponse(List<ProductDto> products) {
        this.products = products;
    }

    public ListOfProductsResponse(String errorDesciption) {
        setErrorDescription(errorDesciption);
    }

    public ListOfProductsResponse(LocalDateTime date, String errorDescription, String errorCode, String description, List<ProductDto> products) {
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
