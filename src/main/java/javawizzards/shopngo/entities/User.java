package javawizzards.shopngo.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String googleId;
    private String email;
    private String username;
    private String pictureUrl;
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @ManyToMany
    @JoinTable(
            name = "user_categories",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public User() {}

    public User(UUID id, String googleId, String email, String username,
                String pictureUrl, String phoneNumber, Set<Order> orders,
                Cart cart, Set<Category> categories) {
        this.id = id;
        this.googleId = googleId;
        this.email = email;
        this.username = username;
        this.pictureUrl = pictureUrl;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
        this.cart = cart;
        this.categories = categories;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Order> getOrders() { return orders; }
    public void setOrders(Set<Order> orders) { this.orders = orders; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public Set<Category> getCategories() { return categories; }
    public void setCategories(Set<Category> categories) { this.categories = categories; }
}
