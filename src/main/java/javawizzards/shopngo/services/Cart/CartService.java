package javawizzards.shopngo.services.Cart;

import javawizzards.shopngo.dtos.Cart.CartDto;
import javawizzards.shopngo.dtos.Cart.CartItemDto;
import javawizzards.shopngo.dtos.Cart.CartSummaryDto;
import javawizzards.shopngo.exceptions.CartCustomException;

import java.util.UUID;

public interface CartService {
    CartDto getUserCart(UUID userId);
    CartDto addItemToCart(UUID userId, UUID productId, int quantity);
    CartDto updateItemQuantity(UUID userId, UUID productId, int newQuantity);
    CartDto removeFromCart(UUID userId, UUID productId);
    CartDto mergeCart(UUID userId, CartDto anonymousCart);
    CartDto clearCart(UUID userId);
    CartSummaryDto getCartSummary(UUID userId);
}
