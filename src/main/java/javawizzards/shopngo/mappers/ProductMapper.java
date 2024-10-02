package javawizzards.shopngo.mappers;

import javawizzards.shopngo.dtos.Product.Create.CreateProductDto;
import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Product.Update.UpdateProductDto;
import javawizzards.shopngo.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    //Object Map implementations
    public ProductDto toProductDTO(Product product) {
        return new ProductDto(
                product.getId(),
                product.getQuantity(),
                product.getPrice(),
                product.getDescription(),
                product.getName()
        );
    }

    public Product toProductFromProductDto(ProductDto productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }

    public Product toProductFromCreateProductDto(CreateProductDto createProductDto) {
        Product product = new Product();
        product.setName(createProductDto.getName());
        product.setDescription(createProductDto.getDescription());
        product.setPrice(createProductDto.getPrice());
        product.setQuantity(createProductDto.getQuantity());
        return product;
    }

    public Product toProductFromUpdateProductDto(UpdateProductDto updateProductDto) {
        Product product = new Product();
        product.setName(updateProductDto.getName());
        product.setDescription(updateProductDto.getDescription());
        product.setPrice(updateProductDto.getPrice());
        product.setQuantity(updateProductDto.getQuantity());
        return product;
    }

    //List Map implementations
    public List<ProductDto> toProductDtoList(List<Product> products) {
        return products.stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }

    public List<Product> toProductListFromProductDtoList(List<ProductDto> productDTOs) {
        return productDTOs.stream()
                .map(this::toProductFromProductDto)
                .collect(Collectors.toList());
    }

    public List<Product> toProductListFromCreateProductDtoList(List<CreateProductDto> productDTOs) {
        return productDTOs.stream()
                .map(this::toProductFromCreateProductDto)
                .collect(Collectors.toList());
    }
}
