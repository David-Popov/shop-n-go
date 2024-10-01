package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.Create.CreateProductRequest;
import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> GetAllProducts() {
        try{
            return this.productMapper.toProductDtoList(this.productRepository.findAll());
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Product GetProductById(int id) {
        return null;
    }

    @Override
    public List<ProductDto> CreateProducts(CreateProductRequest product) {
        try{
            var mappedProductList = this.productMapper.toProductListFromCreateProductDtoList(product.getProduct());
            var data = this.productRepository.saveAll(mappedProductList);
            return this.productMapper.toProductDtoList(data);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void UpdateProduct(Product product) {

    }

    @Override
    public void DeleteProduct(int id) {

    }
}
