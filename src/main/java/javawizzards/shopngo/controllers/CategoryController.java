package javawizzards.shopngo.controllers;

import javawizzards.shopngo.dtos.Category.Request.CategoryDto;
import javawizzards.shopngo.dtos.Category.Response.CategoryResponse;
import javawizzards.shopngo.dtos.Category.Response.CategoriesListResponse;
import javawizzards.shopngo.mappers.CategoryMapper;
import javawizzards.shopngo.services.Category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<CategoriesListResponse> getCategories() {
        try{
            var categories = this.categoryMapper.toCategoryDtoList(categoryService.getCategories());
            var response = new CategoriesListResponse(LocalDateTime.now(), categories);
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> createCategories(@RequestBody CategoryDto category) {
        try{
            if (category == null){
                var response = new CategoryResponse(LocalDateTime.now(), "Invalid Category Data!" ,null);
                return ResponseEntity.badRequest().body(response);
            }

            var categories = this.categoryMapper.toCategoryDto(categoryService.createCategory(category));
            var response = new CategoryResponse(LocalDateTime.now(), categories);
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategories(@PathVariable long id, @RequestBody CategoryDto category) {
        try{
            if (category == null){
                var response = new CategoryResponse(LocalDateTime.now(), "Invalid Category Data!" ,null);
                return ResponseEntity.badRequest().body(response);
            }

            var categories = this.categoryMapper.toCategoryDto(categoryService.updateCategory(id, category));
            var response = new CategoryResponse(LocalDateTime.now(), categories);
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable long id) {
        try{
            if (id < 0){
                var response = new CategoryResponse(LocalDateTime.now(), "Invalid category Id!" ,null);
                return ResponseEntity.badRequest().body(response);
            }

            var categories = this.categoryMapper.toCategoryDto(categoryService.deleteCategory(id));
            var response = new CategoryResponse(LocalDateTime.now(), categories);
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
