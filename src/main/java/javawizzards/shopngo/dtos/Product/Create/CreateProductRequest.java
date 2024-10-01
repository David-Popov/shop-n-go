package javawizzards.shopngo.dtos.Product.Create;

import javawizzards.shopngo.dtos.Request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CreateProductRequest extends Request {
    private List<CreateProductDto> product;

    public CreateProductRequest() {
    }

    public CreateProductRequest(List<CreateProductDto> product, String requestId, LocalDateTime date) {
        this.product = product;
        this.setRequestId(requestId);
        this.setDate(date);
    }

    public List<CreateProductDto> getProduct() {
        return product;
    }

    public void setProduct(List<CreateProductDto> product) {
        this.product = product;
    }
}
