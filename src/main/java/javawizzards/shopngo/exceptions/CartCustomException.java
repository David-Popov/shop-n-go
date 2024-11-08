package javawizzards.shopngo.exceptions;

import javawizzards.shopngo.enumerations.CartMessages;

public class CartCustomException {
    public static class CartNotFoundException extends RuntimeException {
        public CartNotFoundException() {
            super(CartMessages.CART_NOT_FOUND.getMessage());
        }
    }

    public static class CartItemNotFoundException extends RuntimeException {
        public CartItemNotFoundException() {
            super(CartMessages.CART_ITEM_NOT_FOUND.getMessage());
        }
    }

    public static class ItemAdditionFailedException extends RuntimeException {
        public ItemAdditionFailedException() {
            super(CartMessages.ITEM_ADDITION_FAILED.getMessage());
        }
    }

    public static class ItemRemovalFailedException extends RuntimeException {
        public ItemRemovalFailedException() {
            super(CartMessages.ITEM_REMOVAL_FAILED.getMessage());
        }
    }

    public static class CartModificationFailedException extends RuntimeException {
        public CartModificationFailedException(Throwable cause) {
            super(CartMessages.CART_UPDATE_FAILED.getMessage(), cause);
        }
    }

    public static class CartRetrievalFailedException extends RuntimeException {
        public CartRetrievalFailedException(Throwable cause) {
            super(CartMessages.CART_NOT_FOUND.getMessage(), cause);
        }
    }

    public static class InvalidQuantityException extends RuntimeException {
        public InvalidQuantityException() {
            super(CartMessages.INVALID_QUANTITY.getMessage());
        }
    }

    public static class TotalPriceUpdateFailedException extends RuntimeException {
        public TotalPriceUpdateFailedException(Throwable cause) {
            super(CartMessages.TOTAL_PRICE_UPDATE_FAILED.getMessage(), cause);
        }
    }

    public static class InsufficientStockException extends RuntimeException {
        public InsufficientStockException() {
            super(CartMessages.INSUFFICIENT_STOCK.getMessage());
        }
    }

    public static class CartExpiredException extends RuntimeException {
        public CartExpiredException() {
            super(CartMessages.CART_EXPIRED.getMessage());
        }
    }
}