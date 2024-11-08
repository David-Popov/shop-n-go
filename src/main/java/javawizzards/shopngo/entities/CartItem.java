package javawizzards.shopngo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;
    private BigDecimal totalPrice;

    @PrePersist
    @PreUpdate
    protected void calculateTotalPrice() {
        this.totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public void incrementQuantity() {
        this.quantity++;
        calculateTotalPrice();
    }

    public void decrementQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
            calculateTotalPrice();
        }
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity > 0) {
            this.quantity = newQuantity;
            calculateTotalPrice();
        }
    }
}