package javawizzards.shopngo.controllers;

import javawizzards.shopngo.entities.CartItem;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.entities.User;
import javawizzards.shopngo.services.Cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@AuthenticationPrincipal User user) {
        var cartItems = cartService.getCartItems(user);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addProductToCart(@AuthenticationPrincipal User user, @RequestBody Product product, @RequestParam int quantity) {
        var cartItem = cartService.addProductToCart(user, product, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long id) {
        cartService.removeProductFromCart(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long id, @RequestParam int quantity) {
        cartService.updateCartItemQuantity(id, quantity);
        return ResponseEntity.ok().build();
    }
}
