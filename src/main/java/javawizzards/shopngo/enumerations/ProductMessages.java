package javawizzards.shopngo.enumerations;

public enum ProductMessages {
    PRODUCT_NOT_FOUND("Product not found."),
    INVALID_PRODUCT_ID("Invalid product ID."),
    PRODUCT_CREATION_FAILED("Failed to create product."),
    PRODUCT_CREATION_SUCCESS("Product created successfully."),
    PRODUCT_UPDATE_FAILED("Failed to update product."),
    PRODUCT_UPDATE_SUCCESS("Product updated successfully."),
    PRODUCT_DELETE_FAILED("Failed to delete product."),
    PRODUCT_DELETE_SUCCESS("Product deleted successfully."),
    INVALID_PRODUCT_DETAILS("Invalid product details.");

    private final String message;

    ProductMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
