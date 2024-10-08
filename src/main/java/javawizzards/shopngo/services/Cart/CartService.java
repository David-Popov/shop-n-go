package javawizzards.shopngo.services.Cart;

import javawizzards.shopngo.entities.CartItem;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.entities.User;
import javawizzards.shopngo.repositories.CartItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public CartItem addProductToCart(User user, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice().multiply(new BigDecimal(quantity)));
        return cartItemRepository.save(cartItem);
    }

    public void removeProductFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void updateCartItemQuantity(Long cartItemId, int newQuantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
    }
}
