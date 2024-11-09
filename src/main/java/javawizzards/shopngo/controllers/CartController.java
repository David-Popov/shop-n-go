package javawizzards.shopngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import javawizzards.shopngo.dtos.Cart.CartDto;
import javawizzards.shopngo.dtos.Cart.CartItemDto;
import javawizzards.shopngo.dtos.Cart.CartSummaryDto;
import javawizzards.shopngo.dtos.Response;
import javawizzards.shopngo.enumerations.CartMessages;
import javawizzards.shopngo.exceptions.CartCustomException;
import javawizzards.shopngo.services.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Cart management endpoints")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get user's cart", description = "Retrieves the current cart for the specified user")
    public ResponseEntity<Response<CartDto>> getUserCart(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        try {
            CartDto cart = cartService.getUserCart(userId);
            return ResponseEntity.ok(new Response<>(cart, HttpStatus.OK, ""));
        } catch (CartCustomException.CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>(e.getMessage(), HttpStatus.NOT_FOUND, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.CART_NOT_FOUND.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PostMapping("/{userId}/items")
    @Operation(summary = "Add item to cart", description = "Adds an item to the user's cart")
    public ResponseEntity<Response<CartDto>> addItemToCart(
            @Parameter(description = "User ID") @PathVariable UUID userId,
            @Parameter(description = "Product ID") @RequestParam UUID productId,
            @Parameter(description = "Quantity") @RequestParam int quantity) {
        try {
            CartDto updatedCart = cartService.addItemToCart(userId, productId, quantity);
            return ResponseEntity.ok(new Response<>(updatedCart, HttpStatus.OK,
                    CartMessages.ITEM_ADDITION_SUCCESS.getMessage()));
        } catch (CartCustomException.InvalidQuantityException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>(e.getMessage(), HttpStatus.BAD_REQUEST, ""));
        } catch (CartCustomException.InsufficientStockException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>(e.getMessage(), HttpStatus.BAD_REQUEST, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.ITEM_ADDITION_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PutMapping("/{userId}/items/{productId}")
    @Operation(summary = "Update item quantity", description = "Updates the quantity of a specific item in the cart")
    public ResponseEntity<Response<CartDto>> updateItemQuantity(
            @Parameter(description = "User ID") @PathVariable UUID userId,
            @Parameter(description = "Product ID") @PathVariable UUID productId,
            @Parameter(description = "New quantity") @RequestBody @Valid CartItemDto cartItemDto) {
        try {
            CartDto updatedCart = cartService.updateItemQuantity(userId, productId, cartItemDto.getQuantity());
            return ResponseEntity.ok(new Response<>(updatedCart, HttpStatus.OK,
                    CartMessages.CART_UPDATE_SUCCESS.getMessage()));
        } catch (CartCustomException.InvalidQuantityException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>(e.getMessage(), HttpStatus.BAD_REQUEST, ""));
        } catch (CartCustomException.InsufficientStockException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>(e.getMessage(), HttpStatus.BAD_REQUEST, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.CART_UPDATE_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/items/{productId}")
    @Operation(summary = "Remove item from cart", description = "Removes a specific item from the cart")
    public ResponseEntity<Response<CartDto>> removeItemFromCart(
            @Parameter(description = "User ID") @PathVariable UUID userId,
            @Parameter(description = "Product ID") @PathVariable UUID productId) {
        try {
            CartDto updatedCart = cartService.removeFromCart(userId, productId);
            return ResponseEntity.ok(new Response<>(updatedCart, HttpStatus.OK,
                    CartMessages.ITEM_REMOVAL_SUCCESS.getMessage()));
        } catch (CartCustomException.CartItemNotFoundException e) {
            return ResponseEntity.notFound()
                    .build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.ITEM_REMOVAL_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/clear")
    @Operation(summary = "Clear cart", description = "Removes all items from the cart")
    public ResponseEntity<Response<CartDto>> clearCart(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        try {
            CartDto clearedCart = cartService.clearCart(userId);
            return ResponseEntity.ok(new Response<>(clearedCart, HttpStatus.OK,
                    CartMessages.CART_CLEAR_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.CART_UPDATE_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/{userId}/summary")
    @Operation(summary = "Get cart summary", description = "Retrieves a summary of the cart including total items and price")
    public ResponseEntity<Response<CartSummaryDto>> getCartSummary(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        try {
            CartSummaryDto summary = cartService.getCartSummary(userId);
            return ResponseEntity.ok(new Response<>(summary, HttpStatus.OK, ""));
        } catch (CartCustomException.CartNotFoundException e) {
            return ResponseEntity.notFound()
                    .build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.INVALID_CART_OPERATION.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PostMapping("/{userId}/merge")
    @Operation(summary = "Merge carts", description = "Merges an anonymous cart with the user's cart")
    public ResponseEntity<Response<CartDto>> mergeCart(
            @Parameter(description = "User ID") @PathVariable UUID userId,
            @RequestBody CartDto anonymousCart) {
        try {
            CartDto mergedCart = cartService.mergeCart(userId, anonymousCart);
            return ResponseEntity.ok(new Response<>(mergedCart, HttpStatus.OK,
                    CartMessages.CART_UPDATE_SUCCESS.getMessage()));
        } catch (CartCustomException.InsufficientStockException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>(e.getMessage(), HttpStatus.BAD_REQUEST, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(CartMessages.CART_MERGE_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
}