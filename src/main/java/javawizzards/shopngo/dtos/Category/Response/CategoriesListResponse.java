package javawizzards.shopngo.dtos.Category.Response;

import javawizzards.shopngo.dtos.Category.Request.CategoryDto;
import javawizzards.shopngo.dtos.Response;

import java.time.LocalDateTime;
import java.util.List;

public class CategoriesListResponse extends Response {
    List<CategoryDto> categories;

    public CategoriesListResponse(LocalDateTime date, String errorDescription, String errorCode, String description, List<CategoryDto> categories) {
        super(date, errorDescription, errorCode, description);
        this.categories = categories;
    }

    public CategoriesListResponse(LocalDateTime date, String errorDescription, List<CategoryDto> categories) {
        super(date, errorDescription);
        this.categories = categories;
    }

    public CategoriesListResponse(LocalDateTime date, List<CategoryDto> categories) {
        super(date);
        this.categories = categories;
    }

    public CategoriesListResponse(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
