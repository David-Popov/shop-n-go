package javawizzards.shopngo.services.Category;

import javawizzards.shopngo.dtos.Category.Request.CategoryDto;
import javawizzards.shopngo.entities.Category;
import javawizzards.shopngo.mappers.CategoryMapper;
import javawizzards.shopngo.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getCategories() {
        try{
            var categories = this.categoryRepository.findAll();

            if (categories.isEmpty()) {
                return new ArrayList<>();
            }

            return categories;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Category getCategoryById(long id) {
        try{
            var category = this.categoryRepository.findById(id).stream().findFirst().orElse(null);

            if (category == null) {
                return null;
            }

            return category;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        try{
            var categoryForCreate = this.categoryMapper.toCategory(categoryDto);
            this.categoryRepository.save(categoryForCreate);
            return categoryForCreate;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Category updateCategory(long id, CategoryDto categoryDto) {
        try{
            var categoryForUpdate = this.categoryRepository.findByName(categoryDto.getName());

            if (categoryForUpdate == null) {
                return null;
            }

            categoryForUpdate.setName(categoryDto.getName());
            this.categoryRepository.save(categoryForUpdate);
            return categoryForUpdate;
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Category deleteCategory(long id) {
        try{
            var categoryForUpdate = this.categoryRepository.findById(id).stream().findFirst().orElse(null);

            if (categoryForUpdate == null) {
                return null;
            }

            this.categoryRepository.delete(categoryForUpdate);
            return categoryForUpdate;
        } catch (Exception e) {
            throw e;
        }
    }
}
