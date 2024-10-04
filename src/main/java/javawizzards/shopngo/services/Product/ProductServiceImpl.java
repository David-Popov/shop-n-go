package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.Request.ProductDto;
import javawizzards.shopngo.entities.Category;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.repositories.ProductRepository;
import javawizzards.shopngo.services.Category.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> GetAllProducts() {
        try{
            return this.productRepository.findAll();
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Product FindProductById(long id) {
        try{
            var product = this.FindById(id);

            if (product == null) {
                return null;
            }

            return product;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Product> createProducts(List<ProductDto> productDtos) {
        try {
            List<Product> mappedProductList = new ArrayList<>();

            for (ProductDto productDto : productDtos) {
                Category category = this.categoryService.getCategoryById(productDto.getCategoryId());

                Product product = null;

                if (category == null) {
                    product.setCategory(null);
                }
                else {
                    product = this.productMapper.toProductFromProductDto(productDto, category);
                }

                mappedProductList.add(product);
            }

            return this.productRepository.saveAll(mappedProductList);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Product UpdateProduct(long id, ProductDto product) {
        try{
            var productForUpdate = this.FindById(id);
            var category = this.categoryService.getCategoryById(product.getCategoryId());

            if (productForUpdate == null) {
                return null;
            }

            productForUpdate.setName(product.getName());
            productForUpdate.setDescription(product.getDescription());
            productForUpdate.setPrice(product.getPrice());
            productForUpdate.setQuantity(product.getQuantity());
            productForUpdate.setRating(product.getRating());
            productForUpdate.setImageUrl(product.getImageUrl());
            productForUpdate.setCategory(category);

            this.productRepository.save(productForUpdate);
            return productForUpdate;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Product DeleteProduct(long id) {
        try{
            var productForDelete = this.FindById(id);

            if (productForDelete == null) {
                return null;
            }

            productForDelete.setIsDeleted(true);
            return productForDelete;
        } catch (Exception e) {
            throw e;
        }
    }

    private Product FindById(long id) {
        return this.productRepository.findById(id).stream().findFirst().orElse(null);
    }
}
