package javawizzards.shopngo.exceptions;

import javawizzards.shopngo.enumerations.OrderMessages;

public class OrderCustomException {

    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException() {
            super(OrderMessages.ORDER_NOT_FOUND.getMessage());
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException() {
            super(OrderMessages.USER_NOT_FOUND.getMessage());
        }
    }

    public static class EmptyCartException extends RuntimeException {
        public EmptyCartException() {
            super(OrderMessages.EMPTY_CART.getMessage());
        }
    }

    public static class InsufficientStockException extends RuntimeException {
        public InsufficientStockException(String productName) {
            super(String.format(OrderMessages.INSUFFICIENT_STOCK.getMessage(), productName));
        }
    }

    public static class OrderNotCancellableException extends RuntimeException {
        public OrderNotCancellableException() {
            super(OrderMessages.ORDER_NOT_CANCELLABLE.getMessage());
        }
    }

    public static class UnauthorizedAccessException extends RuntimeException {
        public UnauthorizedAccessException() {
            super(OrderMessages.UNAUTHORIZED_ACCESS.getMessage());
        }
    }

    public static class InvalidOrderStatusException extends RuntimeException {
        public InvalidOrderStatusException() {
            super(OrderMessages.INVALID_ORDER_STATUS.getMessage());
        }
    }

    public static class OrderCreationFailedException extends RuntimeException {
        public OrderCreationFailedException(Throwable cause) {
            super(OrderMessages.ORDER_CREATION_FAILED.getMessage(), cause);
        }
    }

    public static class OrderUpdateFailedException extends RuntimeException {
        public OrderUpdateFailedException(Throwable cause) {
            super(OrderMessages.ORDER_UPDATE_FAILED.getMessage(), cause);
        }
    }
}