package javawizzards.shopngo.services.Cart;

import javawizzards.shopngo.dtos.Cart.CartDto;
import javawizzards.shopngo.dtos.Cart.CartItemDto;
import javawizzards.shopngo.dtos.Cart.CartSummaryDto;
import javawizzards.shopngo.entities.Cart;
import javawizzards.shopngo.entities.CartItem;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.entities.User;
import javawizzards.shopngo.exceptions.CartCustomException;
import javawizzards.shopngo.mappers.CartMapper;
import javawizzards.shopngo.repositories.CartItemRepository;
import javawizzards.shopngo.repositories.CartRepository;
import javawizzards.shopngo.repositories.ProductRepository;
import javawizzards.shopngo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional(readOnly = true)
    public CartDto getUserCart(UUID userId) {
        Cart cart = findOrCreateCart(userId);
        return cartMapper.toCartDto(cart);
    }

    @Override
    @Transactional
    public CartDto addItemToCart(UUID userId, UUID productId, int quantity) {
        if (quantity <= 0) {
            throw new CartCustomException.InvalidQuantityException();
        }

        Cart cart = findOrCreateCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(CartCustomException.CartItemNotFoundException::new);

        // Check product stock
        if (product.getQuantity() < quantity) {
            throw new CartCustomException.InsufficientStockException();
        }

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            // Check if new total quantity exceeds stock
            if (product.getQuantity() < (item.getQuantity() + quantity)) {
                throw new CartCustomException.InsufficientStockException();
            }
            item.updateQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.addItem(newItem);
        }

        cart.calculateTotalPrice();
        cart = cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    @Override
    @Transactional
    public CartDto updateItemQuantity(UUID userId, UUID productId, int newQuantity) {
        Cart cart = findOrCreateCart(userId);
        CartItem item = findCartItem(cart, productId);
        Product product = item.getProduct();

        if (newQuantity <= 0) {
            cart.removeItem(item);
        } else {
            if (product.getQuantity() < newQuantity) {
                throw new CartCustomException.InsufficientStockException();
            }
            item.updateQuantity(newQuantity);
        }

        cart.calculateTotalPrice();
        cart = cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    @Override
    @Transactional
    public CartDto removeFromCart(UUID userId, UUID productId) {
        Cart cart = findOrCreateCart(userId);
        CartItem item = findCartItem(cart, productId);
        cart.removeItem(item);
        cart = cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    @Override
    @Transactional
    public CartDto mergeCart(UUID userId, CartDto anonymousCart) {
        Cart userCart = findOrCreateCart(userId);

        for (CartItemDto itemDTO : anonymousCart.getItems()) {
            try {
                addItemToCart(userId, itemDTO.getProductId(), itemDTO.getQuantity());
            } catch (CartCustomException.InsufficientStockException e) {
                // Skip items that exceed available stock
                continue;
            }
        }

        userCart = cartRepository.save(userCart);
        return cartMapper.toCartDto(userCart);
    }

    @Override
    @Transactional
    public CartDto clearCart(UUID userId) {
        Cart cart = findOrCreateCart(userId);
        cart.clear();
        cart = cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartSummaryDto getCartSummary(UUID userId) {
        Cart cart = findOrCreateCart(userId);
        return new CartSummaryDto(
                cart.getItems().stream().mapToInt(CartItem::getQuantity).sum(),
                cart.getTotalPrice()
        );
    }

    private Cart findOrCreateCart(UUID userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(CartCustomException.CartNotFoundException::new);

            Cart newCart = new Cart();
            newCart.setUser(user);
            user.setCart(newCart);
            return cartRepository.save(newCart);
        });
    }

    private CartItem findCartItem(Cart cart, UUID productId) {
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(CartCustomException.CartItemNotFoundException::new);
    }

    @Scheduled(cron = "0 0 1 * * ?") // Run at 1 AM every day
    @Transactional
    public void cleanupExpiredCarts() {
        LocalDateTime expiryThreshold = LocalDateTime.now();
        List<Cart> expiredCarts = cartRepository.findByExpiryDateBefore(expiryThreshold);

        for (Cart cart : expiredCarts) {
            cart.clear();
        }

        cartRepository.saveAll(expiredCarts);
    }

    @Transactional(readOnly = true)
    public boolean validateCartItems(Cart cart) {
        return cart.getItems().stream().allMatch(item -> {
            Product product = productRepository.findById(item.getProduct().getId()).orElse(null);
            return product != null && product.getQuantity() >= item.getQuantity();
        });
    }
}