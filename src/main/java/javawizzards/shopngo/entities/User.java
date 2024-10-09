package javawizzards.shopngo.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String googleId;
    private String name;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<CartItem> cart;

    // getters and setters

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<CartItem> getCart() {
        return cart;
    }
}
