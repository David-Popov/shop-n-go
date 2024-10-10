package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.Request.ProductDto;
import javawizzards.shopngo.entities.Category;
import javawizzards.shopngo.entities.Discount;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.repositories.ProductRepository;
import javawizzards.shopngo.services.Category.CategoryService;
import javawizzards.shopngo.services.Discount.DiscountService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final DiscountService discountService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService, DiscountService discountService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
        this.discountService = discountService;
    }

    @Override
    public List<Product> GetAllProducts() {
        try{
            return this.productRepository.findAll()
                    .stream()
                    .filter(product -> !product.getIsDeleted())
                    .collect(Collectors.toList());
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
        List<Product> mappedProductList = new ArrayList<>();
        List<Discount> discounts = this.discountService.getDiscounts();

        for (ProductDto productDto : productDtos) {
            Category category = this.categoryService.getCategoryById(productDto.getCategoryId());

            Discount discount = discounts.stream()
                    .filter(d -> d.getId() == productDto.getDiscountId())
                    .findFirst()
                    .orElse(null);

            Product product = this.productMapper.toProductFromProductDto(productDto, category, discount);

            mappedProductList.add(product);
        }

        return this.productRepository.saveAll(mappedProductList);
    }

    @Override
    public Product UpdateProduct(long id, ProductDto productDto) {
        try {
            Product productForUpdate = this.FindById(id);
            Category category = this.categoryService.getCategoryById(productDto.getCategoryId());
            Discount discount = (productDto.getDiscountId() > 0) ? this.discountService.getDiscountById(productDto.getDiscountId()) : null;

            if (productForUpdate == null) {
                return null;
            }

            productForUpdate.setName(productDto.getName());
            productForUpdate.setDescription(productDto.getDescription());
            productForUpdate.setPrice(productDto.getPrice());
            productForUpdate.setQuantity(productDto.getQuantity());
            productForUpdate.setRating(productDto.getRating());
            productForUpdate.setImageUrl(productDto.getImageUrl());
            productForUpdate.setCategory(category);
            productForUpdate.setDiscount(discount); // Update discount

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
