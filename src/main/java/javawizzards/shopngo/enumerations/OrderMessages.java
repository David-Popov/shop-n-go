package javawizzards.shopngo.enumerations;

public enum OrderMessages {
    ORDER_NOT_FOUND("Order not found."),
    USER_NOT_FOUND("User not found."),
    EMPTY_CART("Cannot create order with empty cart."),
    INSUFFICIENT_STOCK("Insufficient stock for product: %s"),
    ORDER_CREATION_FAILED("Failed to create order."),
    ORDER_CREATION_SUCCESS("Order created successfully."),
    ORDER_UPDATE_FAILED("Failed to update order."),
    ORDER_UPDATE_SUCCESS("Order updated successfully."),
    ORDER_CANCELLATION_FAILED("Failed to cancel order."),
    ORDER_CANCELLATION_SUCCESS("Order cancelled successfully."),
    ORDER_NOT_CANCELLABLE("Order cannot be cancelled in its current state."),
    UNAUTHORIZED_ACCESS("Unauthorized access to order."),
    INVALID_ORDER_STATUS("Invalid order status."),
    INVALID_DATE_RANGE("Invalid date range provided."),
    ORDER_RETRIEVAL_FAILED("Failed to retrieve orders.");

    private final String message;

    OrderMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}