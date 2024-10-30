package javawizzards.shopngo.services.Cart;

import javawizzards.shopngo.dtos.Cart.CartDto;
import javawizzards.shopngo.dtos.Cart.CartItemDto;
import javawizzards.shopngo.dtos.Cart.CartSummaryDto;
import javawizzards.shopngo.entities.Cart;
import javawizzards.shopngo.entities.CartItem;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.entities.User;
import javawizzards.shopngo.exceptions.CartCustomException;
import javawizzards.shopngo.repositories.CartItemRepository;
import javawizzards.shopngo.repositories.CartRepository;
import javawizzards.shopngo.repositories.ProductRepository;
import javawizzards.shopngo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDto getUserCart(UUID userId) {
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(CartCustomException.CartNotFoundException::new);
            return modelMapper.map(cart, CartDto.class);
        } catch (Exception e) {
            throw new CartCustomException.CartRetrievalFailedException(e);
        }
    }

    @Override
    public CartDto addItemToCart(UUID userId, UUID productId, int quantity) {
        try {
            Cart cart = findOrCreateCart(userId);
            Product product = productRepository.findById(productId)
                    .orElseThrow(CartCustomException.CartItemNotFoundException::new);

            if (quantity <= 0) {
                throw new CartCustomException.InvalidQuantityException();
            }

            CartItem item = cart.getItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            } else {
                item = new CartItem(UUID.randomUUID(), product, quantity, product.getPrice().multiply(BigDecimal.valueOf(quantity)));
                cart.getItems().add(item);
            }

            updateTotalPrice(cart);
            cartRepository.save(cart);
            return this.modelMapper.map(cart, CartDto.class);

        } catch (CartCustomException.CartItemNotFoundException | CartCustomException.InvalidQuantityException e) {
            throw e;
        } catch (Exception e) {
            throw new CartCustomException.ItemAdditionFailedException();
        }
    }

    @Override
    public CartDto updateItemQuantity(UUID userId, UUID productId, int newQuantity) {
        try {
            Cart cart = getUserCartPrivate(userId);
            CartItem item = findCartItem(cart, productId);

            if (newQuantity <= 0) {
                cart.getItems().remove(item);
            } else {
                item.setQuantity(newQuantity);
                item.setTotalPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            }

            updateTotalPrice(cart);
            cartRepository.save(cart);
            return this.modelMapper.map(cart, CartDto.class);

        } catch (CartCustomException.CartItemNotFoundException | CartCustomException.InvalidQuantityException e) {
            throw e;
        } catch (Exception e) {
            throw new CartCustomException.CartModificationFailedException(e);
        }
    }

    @Override
    public CartDto removeFromCart(UUID userId, UUID productId) {
        try {
            Cart cart = getUserCartPrivate(userId);
            CartItem item = findCartItem(cart, productId);

            cart.getItems().remove(item);
            updateTotalPrice(cart);

            cartRepository.save(cart);
            return this.modelMapper.map(cart, CartDto.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CartDto mergeCart(UUID userId, CartDto anonymousCart) {
        try {
            Cart userCart = findOrCreateCart(userId);

            for (CartItemDto itemDTO : anonymousCart.getItems()) {
                Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(CartCustomException.CartItemNotFoundException::new);

                addItemToCart(userId, product.getId(), itemDTO.getQuantity());
            }

            cartRepository.save(userCart);
            return this.modelMapper.map(userCart, CartDto.class);

        } catch (Exception e) {
            throw new CartCustomException.CartModificationFailedException(e);
        }
    }

    @Override
    public CartDto clearCart(UUID userId) {
        try {
            Cart cart = getUserCartPrivate(userId);
            cart.getItems().clear();
            cart.setTotalPrice(BigDecimal.ZERO);

            cartRepository.save(cart);
            return this.modelMapper.map(cart, CartDto.class);

        } catch (Exception e) {
            throw new CartCustomException.CartModificationFailedException(e);
        }
    }

    @Override
    public CartSummaryDto getCartSummary(UUID userId) {
        try {
            Cart cart = getUserCartPrivate(userId);
            int totalItems = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
            BigDecimal totalPrice = cart.getTotalPrice();

            return new CartSummaryDto(totalItems, totalPrice);
        } catch (Exception e) {
            throw new CartCustomException.CartRetrievalFailedException(e);
        }
    }

    private Cart findOrCreateCart(UUID userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId).orElseThrow(CartCustomException.CartNotFoundException::new);
            Cart newCart = new Cart(UUID.randomUUID(), user, new ArrayList<>(), BigDecimal.ZERO);
            user.setCart(newCart);
            return newCart;
        });
    }

    private Cart getUserCartPrivate(UUID userId) {
        return cartRepository.findByUserId(userId).orElseThrow(CartCustomException.CartNotFoundException::new);
    }

    private CartItem findCartItem(Cart cart, UUID productId) {
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(CartCustomException.CartItemNotFoundException::new);
    }

    private void updateTotalPrice(Cart cart) {
        try {
            BigDecimal total = cart.getItems().stream()
                    .map(CartItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            cart.setTotalPrice(total);
        } catch (Exception e) {
            throw new CartCustomException.TotalPriceUpdateFailedException(e);
        }
    }
}
