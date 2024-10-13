package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.Request.ProductDto;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
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

        for (ProductDto productDto : productDtos) {
            Product product = this.productMapper.toProductFromProductDto(productDto);

            mappedProductList.add(product);
        }

        return this.productRepository.saveAll(mappedProductList);
    }

    @Override
    public Product UpdateProduct(long id, ProductDto productDto) {
        try {
            Product productForUpdate = this.FindById(id);

            if (productForUpdate == null) {
                return null;
            }

            productForUpdate.setName(productDto.getName());
            productForUpdate.setDescription(productDto.getDescription());
            productForUpdate.setPrice(productDto.getPrice());
            productForUpdate.setQuantity(productDto.getQuantity());
            productForUpdate.setRating(productDto.getRating());
            productForUpdate.setImageUrl(productDto.getImageUrl());

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
