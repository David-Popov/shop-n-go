package javawizzards.shopngo.dtos.Product.Create;

import java.math.BigDecimal;

public class CreateProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    public CreateProductDto() {
    }

    public CreateProductDto(int quantity, BigDecimal price, String description, String name) {
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
