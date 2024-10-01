package javawizzards.shopngo.dtos.Product;

import javawizzards.shopngo.dtos.Response;

import java.util.List;

public class AllProductsResponse extends Response{
    List<ProductDto> products;

    public AllProductsResponse(List<ProductDto> products) {
        this.products = products;
    }

    public AllProductsResponse(String errorDesciption) {
        setErrorDescription(errorDesciption);
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
