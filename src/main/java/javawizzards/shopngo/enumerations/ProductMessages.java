package javawizzards.shopngo.enumerations;

public enum ProductMessages {
    // Existing messages
    PRODUCT_NOT_FOUND("Product not found."),
    INVALID_PRODUCT_ID("Invalid product ID."),
    PRODUCT_CREATION_FAILED("Failed to create product."),
    PRODUCT_CREATION_SUCCESS("Product created successfully."),
    PRODUCT_UPDATE_FAILED("Failed to update product."),
    PRODUCT_UPDATE_SUCCESS("Product updated successfully."),
    PRODUCT_DELETE_FAILED("Failed to delete product."),
    PRODUCT_DELETE_SUCCESS("Product deleted successfully."),
    INVALID_PRODUCT_DETAILS("Invalid product details."),

    // New messages for enhanced functionality
    PRODUCT_RETRIEVAL_FAILED("Failed to retrieve products."),
    PRODUCT_SEARCH_FAILED("Failed to search products."),
    PRODUCT_FILTER_FAILED("Failed to filter products."),
    PRODUCT_CATEGORY_FILTER_FAILED("Failed to filter products by category."),
    LOW_STOCK_CHECK_FAILED("Failed to check low stock products."),
    INVALID_PRICE_RANGE("Invalid price range specified."),
    INVALID_RATING_VALUE("Invalid rating value specified."),
    INVALID_STOCK_THRESHOLD("Invalid stock threshold specified."),
    INVALID_PAGE_PARAMETERS("Invalid pagination parameters."),
    INVALID_SORT_PARAMETERS("Invalid sort parameters."),
    NO_PRODUCTS_FOUND("No products found matching the criteria.");

    private final String message;

    ProductMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}