package javawizzards.shopngo.dtos.Category.Response;

import javawizzards.shopngo.dtos.Category.Request.CategoryDto;
import javawizzards.shopngo.dtos.Response;

import java.time.LocalDateTime;

public class CategoryResponse extends Response {
    private CategoryDto category;

    public CategoryResponse(CategoryDto category) {
        this.category = category;
    }

    public CategoryResponse(LocalDateTime date, String errorDescription, String errorCode, String description, CategoryDto category) {
        super(date, errorDescription, errorCode, description);
        this.category = category;
    }

    public CategoryResponse(LocalDateTime date, String errorDescription, CategoryDto category) {
        super(date, errorDescription);
        this.category = category;
    }

    public CategoryResponse(LocalDateTime date, CategoryDto category) {
        super(date);
        this.category = category;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
