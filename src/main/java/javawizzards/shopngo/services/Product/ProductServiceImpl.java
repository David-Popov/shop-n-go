package javawizzards.shopngo.services.Product;

import javawizzards.shopngo.dtos.Product.Create.CreateProductRequest;
import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Product.Update.UpdateProductDto;
import javawizzards.shopngo.entities.Product;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProductDto FindProductById(long id) {
        try{
            return productMapper.toProductDTO(this.productRepository.findById(id).orElse(null));
        }
        catch (Exception e){
            throw e;
        }
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
    public void UpdateProduct(UpdateProductDto product) {
        try{
            var productRecord = this.productMapper.toProductFromProductDto(this.FindProductById(product.getId()));
            productRecord.setName(product.getName());
            productRecord.setDescription(product.getDescription());
            productRecord.setPrice(product.getPrice());
            productRecord.setQuantity(product.getQuantity());
            this.productRepository.save(productRecord);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void DeleteProduct(long id) {
        try{
            this.productRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}
