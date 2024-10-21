package javawizzards.shopngo.exceptions;

import javawizzards.shopngo.enumerations.ProductMessages;

public class PorductCustomException {
    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException() {
            super(ProductMessages.PRODUCT_NOT_FOUND.getMessage());
        }
    }

    public static class InvalidProductIdException extends RuntimeException {
        public InvalidProductIdException() {
            super(ProductMessages.INVALID_PRODUCT_ID.getMessage());
        }
    }

    public static class ProductCreationFailedException extends RuntimeException {
        public ProductCreationFailedException() {
            super(ProductMessages.PRODUCT_CREATION_FAILED.getMessage());
        }
    }

    public static class ProductCreationSuccessException extends RuntimeException {
        public ProductCreationSuccessException() {
            super(ProductMessages.PRODUCT_CREATION_SUCCESS.getMessage());
        }
    }

    public static class ProductUpdateFailedException extends RuntimeException {
        public ProductUpdateFailedException() {
            super(ProductMessages.PRODUCT_UPDATE_FAILED.getMessage());
        }
    }

    public static class ProductUpdateSuccessException extends RuntimeException {
        public ProductUpdateSuccessException() {
            super(ProductMessages.PRODUCT_UPDATE_SUCCESS.getMessage());
        }
    }

    public static class ProductDeletionFailedException extends RuntimeException {
        public ProductDeletionFailedException() {
            super(ProductMessages.PRODUCT_DELETE_FAILED.getMessage());
        }
    }

    public static class ProductDeletionSuccessException extends RuntimeException {
        public ProductDeletionSuccessException() {
            super(ProductMessages.PRODUCT_DELETE_SUCCESS.getMessage());
        }
    }

    public static class InvalidProductDetailsException extends RuntimeException {
        public InvalidProductDetailsException() {
            super(ProductMessages.INVALID_PRODUCT_DETAILS.getMessage());
        }
    }
}
