package javawizzards.shopngo.enumerations;

public enum CartMessages {
    CART_NOT_FOUND("Cart not found."),
    CART_ITEM_NOT_FOUND("Cart item not found."),
    ITEM_ADDITION_FAILED("Failed to add item to cart."),
    ITEM_ADDITION_SUCCESS("Item added to cart successfully."),
    ITEM_REMOVAL_FAILED("Failed to remove item from cart."),
    ITEM_REMOVAL_SUCCESS("Item removed from cart successfully."),
    CART_UPDATE_FAILED("Failed to update cart."),
    CART_UPDATE_SUCCESS("Cart updated successfully."),
    CART_CLEAR_SUCCESS("Cart cleared successfully."),
    INVALID_CART_OPERATION("Invalid cart operation."),
    INVALID_QUANTITY("Invalid quantity. Quantity must be greater than zero."),
    TOTAL_PRICE_UPDATE_FAILED("Failed to update the total price.");

    private final String message;

    CartMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
