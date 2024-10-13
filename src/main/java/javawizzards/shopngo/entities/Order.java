package javawizzards.shopngo.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long totalPrice;
    private String deliveryAddress;
    private String deliveryPhoneNumber;
    private String deliveryName;
    private String deliveryEmail;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Status {
        PENDING,
        SENT,
        SHIPPED,
        CANCELLED
    }

    public Order() {}

    public Order(UUID id, User user, List<Product> products, Status status, Long totalPrice, String deliveryAddress, String deliveryPhoneNumber, String deliveryName, String deliveryEmail, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.status = status;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
        this.deliveryName = deliveryName;
        this.deliveryEmail = deliveryEmail;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryPhoneNumber() {
        return deliveryPhoneNumber;
    }

    public void setDeliveryPhoneNumber(String deliveryPhoneNumber) {
        this.deliveryPhoneNumber = deliveryPhoneNumber;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryEmail() {
        return deliveryEmail;
    }

    public void setDeliveryEmail(String deliveryEmail) {
        this.deliveryEmail = deliveryEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
