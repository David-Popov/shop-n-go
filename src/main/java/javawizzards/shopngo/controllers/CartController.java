package javawizzards.shopngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import javawizzards.shopngo.dtos.Cart.CartDto;
import javawizzards.shopngo.dtos.Cart.CartItemDto;
import javawizzards.shopngo.dtos.Cart.CartSummaryDto;
import javawizzards.shopngo.dtos.Response;
import javawizzards.shopngo.enumerations.CartMessages;
import javawizzards.shopngo.services.Cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}/items")
    @Operation(summary = "Add items to cart", description = "Adds items to the user's cart based on the provided cart DTO.")
    public ResponseEntity<Response<CartDto>> addItemToCart(@PathVariable UUID userId, @RequestBody @Valid CartDto cartDto) {
        try {
            for (CartItemDto item : cartDto.getItems()) {
                cartService.addItemToCart(userId, item.getProductId(), item.getQuantity());
            }

            CartDto updatedCart = cartService.getUserCart(userId);
            return ResponseEntity.ok(new Response<>(updatedCart, HttpStatus.OK, CartMessages.ITEM_ADDITION_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.ITEM_ADDITION_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PutMapping("/{userId}/items/{productId}")
    @Operation(summary = "Update item quantity", description = "Updates the quantity of a specific item in the user's cart.")
    public ResponseEntity<Response<CartDto>> updateItemQuantity(@PathVariable UUID userId, @PathVariable UUID productId, @RequestBody @Valid CartItemDto cartItemDto) {
        try {
            CartDto updatedCart = cartService.updateItemQuantity(userId, productId, cartItemDto.getQuantity());
            return ResponseEntity.ok(new Response<>(updatedCart, HttpStatus.OK, CartMessages.CART_UPDATE_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.CART_UPDATE_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/items/{productId}")
    @Operation(summary = "Remove item from cart", description = "Removes a specific item from the user's cart.")
    public ResponseEntity<Response<Void>> removeItemFromCart(@PathVariable UUID userId, @PathVariable UUID productId) {
        try {
            cartService.removeFromCart(userId, productId);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK, CartMessages.ITEM_REMOVAL_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.ITEM_REMOVAL_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/clear")
    @Operation(summary = "Clear cart", description = "Clears all items from the user's cart.")
    public ResponseEntity<Response<CartDto>> clearCart(@PathVariable UUID userId) {
        try {
            CartDto clearedCart = cartService.clearCart(userId);
            return ResponseEntity.ok(new Response<>(clearedCart, HttpStatus.OK, CartMessages.CART_CLEAR_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.CART_UPDATE_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/{userId}/summary")
    @Operation(summary = "Get cart summary", description = "Retrieves a summary of the user's cart, including total items and price.")
    public ResponseEntity<Response<CartSummaryDto>> getCartSummary(@PathVariable UUID userId) {
        try {
            CartSummaryDto summary = cartService.getCartSummary(userId);
            return ResponseEntity.ok(new Response<>(summary, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.INVALID_CART_OPERATION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
}
