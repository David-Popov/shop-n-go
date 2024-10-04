package javawizzards.shopngo.services.Category;

import javawizzards.shopngo.dtos.Category.Request.CategoryDto;
import javawizzards.shopngo.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategoryById(long id);
    Category createCategory(CategoryDto categoryDto);
    Category updateCategory(long id, CategoryDto categoryDto);
    Category deleteCategory(long id);
}
