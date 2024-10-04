package javawizzards.shopngo.mappers;

import javawizzards.shopngo.dtos.Category.Request.CategoryDto;
import javawizzards.shopngo.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setId(dto.getId());
        return category;
    }

    public CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setName(category.getName());
        dto.setId(category.getId());
        return dto;
    }

    public List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryDto)
                .collect(Collectors.toList());
    }
}
