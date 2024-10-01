package javawizzards.shopngo.mappers;

import javawizzards.shopngo.dtos.Product.Create.CreateProductDto;
import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

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
