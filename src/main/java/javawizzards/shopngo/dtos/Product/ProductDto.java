package javawizzards.shopngo.dtos.Product;

import java.math.BigDecimal;

public class ProductDto {
    private long Id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    public ProductDto() {
    }

    public ProductDto(Long id,int quantity, BigDecimal price, String description, String name) {
        this.Id = id;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.name = name;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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
